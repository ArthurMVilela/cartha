package document.persistence.dao.address

import document.address.Municipality
import document.persistence.tables.address.MunicipalityTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import persistence.DAO
import persistence.ResultSet
import java.util.*

class MunicipalityDAO:DAO<Municipality, UUID> {
    override fun insert(obj: Municipality): Municipality {
        var inserted:Municipality? = null

        transaction {
            try {
                val insertedId = MunicipalityTable.insertAndGetId {
                    it[id] = obj.id
                    it[name] = obj.name
                    it[uf] = obj.uf
                }

                inserted = toType(MunicipalityTable.select(Op.build { MunicipalityTable.id eq insertedId }).first())
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return inserted!!
    }

    override fun select(id: UUID): Municipality? {
        var found:Municipality? = null

        transaction {
            try {
                val row = MunicipalityTable.select(Op.build { MunicipalityTable.id eq id }).firstOrNull()?:return@transaction
                found = toType(row)
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return found
    }

    override fun selectMany(condition: Op<Boolean>, page: Int, pageLength: Int): ResultSet<Municipality> {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>): List<Municipality> {
        val results = mutableListOf<Municipality>()

        transaction {
            try {
                val rows = MunicipalityTable.select(condition)

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

    override fun selectAll(page: Int, pageLength: Int): ResultSet<Municipality> {
        TODO("Not yet implemented")
    }

    override fun update(obj: Municipality) {
        TODO("Not yet implemented")
    }

    override fun remove(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun removeWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }

    override fun toType(row: ResultRow): Municipality {
        val id = row[MunicipalityTable.id].value
        val name = row[MunicipalityTable.name]
        val uf = row[MunicipalityTable.uf]

        return Municipality(id,name,uf)
    }
}