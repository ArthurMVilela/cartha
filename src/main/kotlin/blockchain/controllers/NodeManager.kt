package blockchain.controllers

import blockchain.Block
import blockchain.NodeInfo
import blockchain.Transaction
import blockchain.persistence.dao.NodeInfoDAO
import blockchain.persistence.dao.TransactionDAO
import blockchain.persistence.tables.NodeInfoTable
import blockchain.persistence.tables.TransactionTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import persistence.ResultSet
import java.time.LocalDateTime
import java.util.*

/**
 * Gerenciadoe de nós da rede blockchain
 */
class NodeManager () {
    private val nodeInfoDAO = NodeInfoDAO()
    private val transactionDAO = TransactionDAO()
    private val client = NodeClient()

    private val transactionTarget = System.getenv("TRANSACTION_AMOUNT_TARGET")?.toInt()?:1

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
        if (pendingCount >= transactionTarget) {
            val pick = pickNodeToGenerate()
            val transactions = transactionDAO.selectMany(Op.build { TransactionTable.pending eq true })

            try {
                val block = client.createBlock(pick, transactions)

                block.transactions.forEach {
                    transactionDAO.update(it)
                }

                transmitBlock(block)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    fun getPendingTransactions(page: Int = 1):ResultSet<Transaction> {
        return transactionDAO.selectMany(Op.build { TransactionTable.pending eq true }, page)
    }

    fun getTransaction(id: UUID): Transaction? {
        return transactionDAO.select(id)
    }

    fun getTransactionByDocument(id: UUID):List<Transaction> {
        return transactionDAO.selectMany(Op.build { TransactionTable.documentId eq id })
    }

    fun getLastDocumentTransaction(id: UUID):Transaction? {
        return transactionDAO.getLastDocumentTransaction(id)
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

    suspend fun transmitBlock(block: Block) {
        val nodes = nodeInfoDAO.selectMany(Op.build { NodeInfoTable.id neq block.nodeId })

        nodes.forEach {
            try {
                client.sendBlock(it, block)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}