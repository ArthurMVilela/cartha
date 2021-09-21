package blockchain.persistence.dao

import blockchain.BlockInfo
import blockchain.persistence.tables.BlockTable
import persistence.DAO
import persistence.ResultSet
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import kotlin.math.ceil

class BlockInfoDAO:DAO<BlockInfo, UUID>{
    override fun insert(obj: BlockInfo): BlockInfo {
        TODO("Not yet implemented")
    }

    override fun select(id: UUID): BlockInfo? {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>, page: Int, pageLength: Int): ResultSet<BlockInfo> {
        var numberOfPages = 0
        val results = mutableListOf<BlockInfo>()

        transaction {
            try {
                val count = BlockTable
                    .slice(BlockTable.id, BlockTable.timestamp, BlockTable.hash, BlockTable.nodeId)
                    .select(condition)
                    .count()
                numberOfPages = ceil(count/(pageLength * 1.0f)).toInt()

                val rows = BlockTable
                    .slice(BlockTable.id, BlockTable.timestamp, BlockTable.hash, BlockTable.nodeId)
                    .select(condition)
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

    override fun selectMany(condition: Op<Boolean>): List<BlockInfo> {
        TODO("Not yet implemented")
    }

    override fun selectAll(page: Int, pageLength: Int): ResultSet<BlockInfo> {
        return selectMany(Op.TRUE, page, pageLength)
    }

    override fun update(obj: BlockInfo) {
        TODO("Not yet implemented")
    }

    override fun remove(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun removeWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }

    override fun toType(row: ResultRow): BlockInfo {
        val id = row[BlockTable.id].value
        val timestamp = row[BlockTable.timestamp]
        val hash = row[BlockTable.hash]
        val nodeId = row[BlockTable.nodeId]

        return BlockInfo(id, timestamp, hash, nodeId)
    }
}