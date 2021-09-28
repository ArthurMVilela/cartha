package document.persistence.dao.civilRegistry

import document.civilRegistry.Affiliation
import document.persistence.dao.address.MunicipalityDAO
import document.persistence.tables.civilRegistry.AffiliationTable
import document.persistence.tables.civilRegistry.birth.BirthCertificateTable
import document.persistence.tables.person.PhysicalPersonTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import persistence.DAO
import persistence.ResultSet
import java.util.*

class AffiliationDAO:DAO<Affiliation, UUID> {
    private val municipalityDAO = MunicipalityDAO()

    override fun insert(obj: Affiliation): Affiliation {
        var inserted: Affiliation? = null

        transaction {
            try {
                val m = municipalityDAO.insert(obj.municipality)
                val insertedId = AffiliationTable.insertAndGetId {
                    it[id] = obj.id
                    it[personId] = if (obj.personId != null) {
                        EntityID(obj.personId!!, PhysicalPersonTable)
                    } else {
                        null
                    }
                    it[cpf] = obj.cpf
                    it[documentId] = obj.documentId
                    it[name] = obj.name
                    it[municipalityId] = m.id
                }

                inserted = toType(AffiliationTable.select(Op.build { AffiliationTable.id eq insertedId }).first())
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return inserted!!
    }

    override fun select(id: UUID): Affiliation? {
        var found:Affiliation? = null

        transaction {
            try {
                val row = AffiliationTable.select(Op.build { AffiliationTable.id eq id }).firstOrNull()?:return@transaction
                found = toType(row)
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return found
    }

    override fun selectMany(condition: Op<Boolean>, page: Int, pageLength: Int): ResultSet<Affiliation> {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>): List<Affiliation> {
        val results = mutableListOf<Affiliation>()

        transaction {
            try{
                val rows = AffiliationTable.select(condition)

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

    override fun selectAll(page: Int, pageLength: Int): ResultSet<Affiliation> {
        TODO("Not yet implemented")
    }

    override fun update(obj: Affiliation) {
        TODO("Not yet implemented")
    }

    override fun remove(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun removeWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }

    override fun toType(row: ResultRow): Affiliation {
        val id = row[AffiliationTable.id].value
        val personId = row[AffiliationTable.personId]?.value
        val cpf = row[AffiliationTable.cpf]
        val documentId = row[AffiliationTable.documentId].value
        val name = row[AffiliationTable.name]

        val municipality = municipalityDAO.select(row[AffiliationTable.municipalityId].value)!!

        return Affiliation(id, personId, cpf, documentId, name, municipality)
    }
}