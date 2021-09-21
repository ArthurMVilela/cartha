package document.persistence.dao

import document.Notary
import document.persistence.tables.NotaryTable
import newPersistence.DAO
import newPersistence.ResultSet
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import kotlin.math.ceil

class NotaryDAO:DAO<Notary, UUID> {
    override fun insert(obj: Notary): Notary {
        var inserted: Notary? = null

        transaction {
            try {
                val insertedId = NotaryTable.insertAndGetId {
                    it[id] = obj.id
                    it[name] = obj.name
                    it[cnpj] = obj.cnpj
                    it[cns] = obj.cns
                }

                inserted = toType(NotaryTable.select(Op.build { NotaryTable.id eq insertedId }).first())
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return inserted!!
    }

    override fun select(id: UUID): Notary? {
        var found:Notary? = null

        transaction {
            try {
                val row = NotaryTable.select(Op.build { NotaryTable.id eq id }).firstOrNull()?:return@transaction
                found = toType(row)
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return found
    }

    fun select(cnpj: String): Notary? {
        var found:Notary? = null

        transaction {
            try {
                val row = NotaryTable.select(Op.build { NotaryTable.cnpj eq cnpj }).firstOrNull()?:return@transaction
                found = toType(row)
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return found
    }

    override fun selectMany(condition: Op<Boolean>, page: Int, pageLength: Int): ResultSet<Notary> {
        var numberOfPages = 0
        val results = mutableListOf<Notary>()

        transaction {
            try {
                val count = NotaryTable.select(condition).count()
                numberOfPages = ceil(count/(pageLength * 1.0f)).toInt()

                val rows = NotaryTable.select(condition).limit((pageLength * page), ((page - 1) * pageLength).toLong())

                rows.forEach {
                    results.add(toType(it))
                }
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return ResultSet(results, page, numberOfPages, pageLength)
    }

    override fun selectMany(condition: Op<Boolean>): List<Notary> {
        TODO("Not yet implemented")
    }

    override fun selectAll(page: Int, pageLength: Int): ResultSet<Notary> {
        return selectMany(Op.TRUE, page, pageLength)
    }

    override fun update(obj: Notary) {
        TODO("Not yet implemented")
    }

    override fun remove(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun removeWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }

    override fun toType(row: ResultRow): Notary {
        val id = row[NotaryTable.id].value
        val name = row[NotaryTable.name]
        val cnpj = row[NotaryTable.cnpj]
        val cns = row[NotaryTable.cns]

        return Notary(id, name, cnpj, cns)
    }
}