package blockchain.persistence.dao

import blockchain.Block
import blockchain.Transaction
import blockchain.persistence.tables.BlockTable
import blockchain.persistence.tables.TransactionTable
import newPersistence.DAO
import newPersistence.ResultSet
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class BlockDAO:DAO<Block, UUID> {
    override fun insert(obj: Block): Block {
        val transactionDAO = TransactionDAO()
        var inserted:Block? = null

        transaction {
            try {
                val insertedId = BlockTable.insertAndGetId {
                    it[id] = obj.id
                    it[timestamp] = obj.timestamp
                    it[transactionsHash] = obj.transactionsHash!!
                    it[previousHash] = obj.previousHash
                    it[hash] = obj.hash!!
                    it[nodeId] = obj.nodeId!!
                }

                obj.transactions.forEach {
                    transactionDAO.insert(it)
                }

                inserted = toType(BlockTable.select(Op.build { BlockTable.id eq insertedId }).first())
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }
        return inserted!!
    }

    override fun select(id: UUID): Block? {
        var found:Block? = null

        transaction {
            try {
                val row = BlockTable.select(Op.build { BlockTable.id eq id }).firstOrNull()?:return@transaction
                found = toType(row)
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return found
    }

    override fun selectMany(condition: Op<Boolean>, page: Int, pageLength: Int): ResultSet<Block> {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>): List<Block> {
        TODO("Not yet implemented")
    }

    override fun selectAll(page: Int, pageLength: Int): ResultSet<Block> {
        TODO("Not yet implemented")
    }

    override fun update(obj: Block) {
        TODO("Not yet implemented")
    }

    override fun remove(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun removeWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }

    override fun toType(row: ResultRow): Block {
        val id = row[BlockTable.id].value
        val timestamp = row[BlockTable.timestamp]
        val transactions = TransactionDAO().selectMany(Op.build { TransactionTable.blockId eq id })
        val transactionsHash = row[BlockTable.transactionsHash]
        val previousHash = row[BlockTable.previousHash]
        val hash = row[BlockTable.hash]
        val nodeId = row[BlockTable.nodeId]

        return Block(id, timestamp, transactions, transactionsHash, previousHash, hash, nodeId)
    }
}