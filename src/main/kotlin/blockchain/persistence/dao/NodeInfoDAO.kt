package blockchain.persistence.dao

import blockchain.NodeInfo
import blockchain.persistence.tables.NodeInfoTable
import newPersistence.DAO
import newPersistence.ResultSet
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import java.util.concurrent.locks.Condition
import kotlin.math.ceil

class NodeInfoDAO:DAO<NodeInfo, UUID> {
    override fun insert(obj: NodeInfo): NodeInfo {
        var inserted:NodeInfo? = null

        transaction {
            try {
                val insertedId = NodeInfoTable.insertAndGetId {
                    it[id] = obj.nodeId
                    it[notaryId] = obj.notaryId
                    it[address] = obj.address
                    it[status] = obj.status
                    it[lastHealthCheck] = obj.lastHealthCheck
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
        var found:NodeInfo? = null

        transaction {
            try {
                val row = NodeInfoTable.select{NodeInfoTable.id eq id}.firstOrNull()?:return@transaction
                found = toType(row)
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return found
    }

    override fun selectMany(condition: Op<Boolean>, page: Int, pageLength: Int): ResultSet<NodeInfo> {
        var numberOfPages = 0
        val results = mutableListOf<NodeInfo>()

        transaction {
            try {
                val count = NodeInfoTable.select(condition).count()
                numberOfPages = ceil(count/(pageLength * 1.0f)).toInt()

                val rows = NodeInfoTable.select(condition)
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

    override fun selectMany(condition: Op<Boolean>): List<NodeInfo> {
        val result = mutableListOf<NodeInfo>()

        transaction {
            try {
                val rows = NodeInfoTable.select(condition)

                rows.forEach {
                    result.add(toType(it))
                }
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return result
    }

    override fun selectAll(page: Int, pageLength: Int): ResultSet<NodeInfo> {
        return selectMany(Op.TRUE, page, pageLength)
    }

    override fun update(obj: NodeInfo) {
        transaction {
            try {
                NodeInfoTable.update({ NodeInfoTable.id eq obj.nodeId}) {
                    with(SqlExpressionBuilder) {
                        it[id] = obj.nodeId
                        it[notaryId] = obj.notaryId
                        it[address] = obj.address
                        it[status] = obj.status
                        it[lastHealthCheck] = obj.lastHealthCheck
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

    override fun toType(row: ResultRow): NodeInfo {
        val nodeId = row[NodeInfoTable.id].value
        val notaryId = row[NodeInfoTable.notaryId]
        val address = row[NodeInfoTable.address]
        val status = row[NodeInfoTable.status]
        val lastHealthCheck = row[NodeInfoTable.lastHealthCheck]

        return NodeInfo(nodeId, notaryId, address, status, lastHealthCheck)
    }
}