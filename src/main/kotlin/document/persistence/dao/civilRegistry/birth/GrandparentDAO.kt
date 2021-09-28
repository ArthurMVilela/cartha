package document.persistence.dao.civilRegistry.birth

import document.civilRegistry.birth.Grandparent
import document.persistence.dao.address.MunicipalityDAO
import document.persistence.tables.civilRegistry.AffiliationTable
import document.persistence.tables.civilRegistry.birth.GrandparentTable
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

class GrandparentDAO:DAO<Grandparent, UUID> {
    private val municipalityDAO = MunicipalityDAO()

    override fun insert(obj: Grandparent): Grandparent {
        var inserted: Grandparent? = null

        transaction {
            try {
                municipalityDAO.insert(obj.municipality)

                val insertedId = GrandparentTable.insertAndGetId {
                    it[personId] = if (obj.personId != null) {
                        EntityID(obj.personId, PhysicalPersonTable)
                    } else {
                        null
                    }
                    it[cpf] = obj.cpf
                    it[birthCertificateId] = obj.birthCertificateId
                    it[name] = obj.name
                    it[type] = obj.type
                    it[municipalityId] = obj.municipality.id
                }

                inserted = toType(GrandparentTable.select(Op.build { GrandparentTable.id eq insertedId }).first())
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return inserted!!
    }

    override fun select(id: UUID): Grandparent? {
        var found: Grandparent? = null

        transaction {
            try {
                val row = GrandparentTable.select(Op.build { GrandparentTable.id eq id }).firstOrNull()?:return@transaction
                found = toType(row)
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return found
    }

    override fun selectMany(condition: Op<Boolean>, page: Int, pageLength: Int): ResultSet<Grandparent> {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>): List<Grandparent> {
        val results = mutableListOf<Grandparent>()

        transaction {
            try {
                val rows = GrandparentTable.select(condition)

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

    override fun selectAll(page: Int, pageLength: Int): ResultSet<Grandparent> {
        TODO("Not yet implemented")
    }

    override fun update(obj: Grandparent) {
        TODO("Not yet implemented")
    }

    override fun remove(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun removeWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }

    override fun toType(row: ResultRow): Grandparent {
        val id = row[GrandparentTable.id].value
        val personId = row[GrandparentTable.personId]?.value
        val birthCertificateId = row[GrandparentTable.birthCertificateId].value
        val cpf = row[GrandparentTable.cpf]
        val name = row[GrandparentTable.name]
        val type = row[GrandparentTable.type]
        val municipality = municipalityDAO.select(row[GrandparentTable.municipalityId].value)!!

        return Grandparent(id, personId, cpf, birthCertificateId, name, type, municipality)
    }
}