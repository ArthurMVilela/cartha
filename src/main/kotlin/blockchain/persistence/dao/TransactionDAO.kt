package blockchain.persistence.dao

import blockchain.Transaction
import blockchain.persistence.tables.TransactionTable
import persistence.DAO
import persistence.ResultSet
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import kotlin.math.ceil

class TransactionDAO:DAO<Transaction, UUID> {
    override fun insert(obj: Transaction): Transaction {
        var inserted:Transaction? = null

        transaction {
            try {
                val insertedId = TransactionTable.insertAndGetId {
                    it[id] = obj.id
                    it[timestamp] = obj.timestamp
                    it[documentId] = obj.documentId
                    it[documentHash] = obj.documentHash
                    it[type] = obj.type
                    it[hash] = obj.hash!!
                    it[pending] = obj.pending
                    it[blockId] = obj.blockId
                }

                inserted = toType(TransactionTable.select(Op.build { TransactionTable.id eq insertedId }).first())
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return inserted!!
    }

    override fun select(id: UUID): Transaction? {
        var found:Transaction? = null

        transaction {
            try {
                val row = TransactionTable.select(Op.build { TransactionTable.id eq id }).firstOrNull()?:return@transaction
                found = toType(row)
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return found
    }

    override fun selectMany(condition: Op<Boolean>, page: Int, pageLength: Int): ResultSet<Transaction> {
        var numberOfPages = 0
        val results = mutableListOf<Transaction>()

        transaction {
            try {
                val count = TransactionTable.select(condition).count()
                numberOfPages = ceil(count/(pageLength * 1.0f)).toInt()

                val rows = TransactionTable.select(condition)
                    .limit((pageLength * page), ((page - 1) * pageLength).toLong())

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

    override fun selectMany(condition: Op<Boolean>): List<Transaction> {
        val results = mutableListOf<Transaction>()

        transaction {
            try {
                val rows = TransactionTable.select(condition)

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

    override fun selectAll(page: Int, pageLength: Int): ResultSet<Transaction> {
        var numberOfPages = 0
        val results = mutableListOf<Transaction>()

        transaction {
            try {
                val count = TransactionTable.select(Op.TRUE).count()
                numberOfPages = ceil(count/(pageLength * 1.0f)).toInt()

                val rows = TransactionTable.select(Op.TRUE)
                    .limit((pageLength * page), ((page - 1) * pageLength).toLong())

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

    override fun update(obj: Transaction) {
        transaction {
            try {
                TransactionTable.update({ TransactionTable.id eq obj.id}) {
                    with(SqlExpressionBuilder) {
                        it[id] = obj.id
                        it[timestamp] = obj.timestamp
                        it[documentId] = obj.documentId
                        it[documentHash] = obj.documentHash
                        it[type] = obj.type
                        it[hash] = obj.hash!!
                        it[pending] = obj.pending
                        it[blockId] = obj.blockId
                    }
                }
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }
    }

    override fun remove(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun removeWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }

    override fun toType(row: ResultRow): Transaction {
        val id = row[TransactionTable.id].value
        val timestamp = row[TransactionTable.timestamp]
        val documentId = row[TransactionTable.documentId]
        val documentHash = row[TransactionTable.documentHash]
        val type = row[TransactionTable.type]
        val hash = row[TransactionTable.hash]
        val pending = row[TransactionTable.pending]
        val blockId = row[TransactionTable.blockId]

        return Transaction(id, timestamp, documentId, documentHash, type, hash, pending, blockId)
    }

    fun getPendingCount(): Int {
        return transaction {
            try {
                TransactionTable.select(Op.build { TransactionTable.pending eq true }).count().toInt()
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }
    }

    fun getLastDocumentTransaction(id: UUID): Transaction? {
        return transaction {
            try {
                val row = TransactionTable.select(Op.build { TransactionTable.documentId eq id })
                    .orderBy(TransactionTable.timestamp, SortOrder.DESC)
                    .firstOrNull()
                if (row != null) {
                    toType(row)
                } else {
                    null
                }

            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }
    }
}