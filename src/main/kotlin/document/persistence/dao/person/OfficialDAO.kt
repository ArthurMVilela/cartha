package document.persistence.dao.person

import document.persistence.tables.person.OfficialTable
import document.persistence.tables.person.PersonTable
import document.person.Official
import persistence.DAO
import persistence.ResultSet
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class OfficialDAO:DAO<Official, UUID> {
    lateinit var table: Join

    init {
        transaction {
            table = OfficialTable.join(PersonTable, JoinType.INNER, additionalConstraint = { OfficialTable.id eq PersonTable.id})
        }
    }

    override fun insert(obj: Official): Official {
        var inserted:Official? = null

        transaction {
            try {
                val insertedId = PersonTable.insertAndGetId {
                    it[id] = obj.id
                    it[name] = obj.name
                    it[accountId] = obj.accountId
                }
                OfficialTable.insert {
                    it[id] = insertedId
                    it[cpf] = obj.cpf
                    it[sex] = obj.sex
                    it[notaryId] = obj.notaryId
                }

                inserted = toType(table.select{ Op.build { OfficialTable.id eq insertedId }}.first())
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return inserted!!
    }

    override fun select(id: UUID): Official? {
        var found:Official? = null

        transaction {
            try {
                val row = table.select(Op.build { OfficialTable.id eq id }).firstOrNull()?:return@transaction
                found = toType(row)
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return found
    }

    fun select(cpf: String): Official? {
        var found:Official? = null

        transaction {
            try {
                val row = table.select(Op.build { OfficialTable.cpf eq cpf }).firstOrNull()?:return@transaction
                found = toType(row)
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return found
    }

    override fun selectMany(condition: Op<Boolean>, page: Int, pageLength: Int): ResultSet<Official> {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>): List<Official> {
        val results = mutableListOf<Official>()

        transaction {
            try {
                val rows = table.select(condition)

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

    override fun selectAll(page: Int, pageLength: Int): ResultSet<Official> {
        TODO("Not yet implemented")
    }

    override fun update(obj: Official) {
        TODO("Not yet implemented")
    }

    override fun remove(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun removeWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }

    override fun toType(row: ResultRow): Official {
        val id = row[OfficialTable.id].value
        val accountId = row[PersonTable.accountId]
        val name = row[PersonTable.name]
        val cpf = row[OfficialTable.cpf]
        val sex = row[OfficialTable.sex]
        val notaryId = row[OfficialTable.notaryId].value

        return Official(id, accountId, name, cpf, sex, notaryId)
    }
}