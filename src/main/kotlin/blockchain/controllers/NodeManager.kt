package blockchain.controllers

import blockchain.*
import blockchain.persistence.dao.NodeInfoDAO
import blockchain.persistence.dao.TransactionDAO
import blockchain.persistence.tables.NodeInfoTable
import blockchain.persistence.tables.TransactionTable
import persistence.ResultSet
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.util.*

/**
 * Gerenciadoe de nós da rede blockchain
 */
class NodeManager (
    val nodes:MutableList<NodeInfo> = mutableListOf<NodeInfo>(),
) {
    val nodeInfoDAO = NodeInfoDAO()
    val transactionDAO = TransactionDAO()
    private val client = NodeClient()

    init {
        transaction {
            SchemaUtils.create(
                NodeInfoTable,
                TransactionTable
            )
        }
    }

    suspend fun addTransactionToQueue(transaction: Transaction) {
        transactionDAO.insert(transaction)

        val pendingCount = transactionDAO.getPendingCount()
        if (pendingCount >= 1) {
            val pick = pickNodeToGenerate()
            val transactions = transactionDAO.selectMany(Op.build { TransactionTable.pending eq true })

            val block = try {
                client.createBlock(pick, transactions)
            } catch (ex: Exception) {
                throw ex
            }

            println(block)
        }
    }

    fun getPendingTransactions(page: Int = 1):ResultSet<Transaction> {
        return transactionDAO.selectMany(Op.build { TransactionTable.pending eq true }, page)
    }

    fun addNode(node: NodeInfo):NodeInfo {
        return nodeInfoDAO.insert(node)
    }

    fun getNode(id: UUID):NodeInfo? {
        return nodeInfoDAO.select(id)
    }

    fun getNodeByNotary(notaryId: UUID):NodeInfo? {
        val result = nodeInfoDAO.selectMany(Op.build { NodeInfoTable.notaryId eq notaryId })
        return result.firstOrNull()
    }

    fun pickNodeToGenerate(): NodeInfo {
        return nodeInfoDAO.getRandom()
    }

    /**
     * Busca os registros de nós cadastrados
     *
     * @param page      pagina da busca (para paginação)
     *
     * @return ResultSet com os nós
     */
    fun getNodes(page: Int = 1):ResultSet<NodeInfo> {
        return nodeInfoDAO.selectAll(page)
    }

    suspend fun checkNodeStatus(node: NodeInfo) {
        node.status = client.healthCheck(node)
        node.lastHealthCheck = LocalDateTime.now()

        nodeInfoDAO.update(node)
    }
}