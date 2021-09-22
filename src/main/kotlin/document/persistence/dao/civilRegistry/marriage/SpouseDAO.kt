package document.persistence.dao.civilRegistry.marriage

import document.civilRegistry.Affiliation
import document.civilRegistry.marriage.Spouse
import document.persistence.dao.civilRegistry.AffiliationDAO
import document.persistence.tables.civilRegistry.AffiliationTable
import document.persistence.tables.civilRegistry.marriage.SpouseAffiliationTable
import document.persistence.tables.civilRegistry.marriage.SpouseTable
import document.persistence.tables.person.PhysicalPersonTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import persistence.DAO
import persistence.ResultSet
import java.util.*
import kotlin.Exception

class SpouseDAO:DAO<Spouse, UUID> {
    private val affiliationDAO = AffiliationDAO()

    override fun insert(obj: Spouse): Spouse {
        var inserted: Spouse? = null

        transaction {
            try {
                val insertedId = SpouseTable.insertAndGetId {
                    it[id] = obj.id
                    it[personId] = if (obj.personId != null) {
                        EntityID(obj.personId, PhysicalPersonTable)
                    } else {
                        null
                    }
                    it[singleName] = obj.singleName
                    it[marriedName] = obj.marriedName
                    it[birthday] = obj.birthday
                    it[nationality] = obj.nationality
                }
                obj.affiliation.forEach {
                    SpouseAffiliationTable.insert { saTable ->
                        saTable[spouseId] = insertedId
                        saTable[affiliationId] = it.id
                    }
                    affiliationDAO.insert(it)
                }

                inserted = toType(SpouseTable.select(Op.build { SpouseTable.id eq insertedId }).first())
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return inserted!!
    }

    override fun select(id: UUID): Spouse? {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>, page: Int, pageLength: Int): ResultSet<Spouse> {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>): List<Spouse> {
        val results = mutableListOf<Spouse>()

        transaction {
            try {
                val rows = SpouseTable.select(condition)

                rows.forEach {
                    results.add(toType(it))
                }
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return results
    }

    override fun selectAll(page: Int, pageLength: Int): ResultSet<Spouse> {
        TODO("Not yet implemented")
    }

    override fun update(obj: Spouse) {
        TODO("Not yet implemented")
    }

    override fun remove(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun removeWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }

    override fun toType(row: ResultRow): Spouse {
        val id = row[SpouseTable.id].value
        val marriageCertificateId = row[SpouseTable.marriageCertificateId].value
        val personId = row[SpouseTable.personId]?.value
        val singleName = row[SpouseTable.singleName]
        val marriedName = row[SpouseTable.marriedName]
        val birthday = row[SpouseTable.birthday]
        val nationality = row[SpouseTable.nationality]
        var affiliation = listOf<Affiliation>()
        transaction {
            try {
                val affiliationIds = mutableListOf<UUID>()
                val saRows = SpouseAffiliationTable.select(Op.build { SpouseAffiliationTable.spouseId eq id })
                saRows.forEach {
                    affiliationIds.add(it[SpouseAffiliationTable.affiliationId].value)
                }
                affiliation = affiliationDAO.selectMany(Op.build { AffiliationTable.id inList affiliationIds })
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return Spouse(id, marriageCertificateId, personId, singleName, marriedName, birthday, nationality, affiliation)
    }
}