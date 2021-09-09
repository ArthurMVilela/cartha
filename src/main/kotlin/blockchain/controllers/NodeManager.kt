package blockchain.controllers

import blockchain.*
import blockchain.persistence.dao.NodeInfoDAO
import blockchain.persistence.dao.TransactionDAO
import blockchain.persistence.tables.NodeInfoTable
import blockchain.persistence.tables.TransactionTable
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import newPersistence.ResultSet
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

    fun addTransactionToQueue(transaction: Transaction) {
        //transactionQueue.add(transaction)
        transactionDAO.insert(transaction)
        //transmitTransaction(transaction)
//        if (transactionQueue.size >= Config.transactionAmount) {
//            val pick = pickNodeToGenerateNextBlock()
//            val transactions = mutableListOf<Transaction>()
//
//            for (i in 0 until Config.transactionAmount) {
//                transactions.add(transactionQueue.remove())
//            }
//
//            val block:Block
//            runBlocking {
//                block = requestBlock(transactions, pick)
//            }
//
//            runBlocking {
//                transmitBlock(block)
//            }
//        }
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

    fun transmitTransaction(transaction: Transaction) {
        TODO("Not implemented yet")
    }

    suspend fun transmitBlock(block:Block) {
//        nodes.forEach {
//            println("${it.id} ${block.nodeId}")
//            if (it.id != block.nodeId) {
//                run {
//                    val block = client.post<Block>("${it.address}/blocks") {
//                        contentType(ContentType.Application.Json)
//                        body = block
//                    }
//                }
//            }
//        }
    }

//    fun pickNodeToGenerateNextBlock(): Node {
//        val pick = nodes.random()
//
//        return pick
//    }

//    suspend fun requestBlock(transactions: List<Transaction>, node: Node): Block {
//        val block = client.post<Block>("${node.address}/blocks/new") {
//            contentType(ContentType.Application.Json)
//            body = transactions
//        }
//
//        return block
//    }
}