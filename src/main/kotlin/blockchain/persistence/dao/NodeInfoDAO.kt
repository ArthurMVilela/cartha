package blockchain.persistence.dao

import blockchain.NodeInfo
import blockchain.persistence.tables.NodeInfoTable
import newPersistence.DAO
import newPersistence.ResultSet
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class NodeInfoDAO:DAO<NodeInfo, UUID> {
    override fun insert(obj: NodeInfo): NodeInfo {
        var inserted:NodeInfo? = null

        transaction {
            try {
                val insertedId = NodeInfoTable.insertAndGetId {
                    it[id] = obj.notaryId
                    it[notaryId] = obj.notaryId
                    it[address] = obj.address
                    it[status] = obj.status
                }

                inserted = toType(NodeInfoTable.select(Op.build { NodeInfoTable.id eq insertedId }).first())
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return inserted!!
    }

    override fun select(id: UUID): NodeInfo? {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>, page: Int, pageLength: Int): ResultSet<NodeInfo> {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>): List<NodeInfo> {
        TODO("Not yet implemented")
    }

    override fun selectAll(page: Int, pageLength: Int): ResultSet<NodeInfo> {
        TODO("Not yet implemented")
    }

    override fun update(obj: NodeInfo) {
        TODO("Not yet implemented")
    }

    override fun remove(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun removeWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }

    override fun toType(row: ResultRow): NodeInfo {
        val nodeId = row[NodeInfoTable.id].value
        val notaryId = row[NodeInfoTable.notaryId]
        val address = row[NodeInfoTable.address]
        val status = row[NodeInfoTable.status]

        return NodeInfo(nodeId, notaryId, address, status)
    }
}