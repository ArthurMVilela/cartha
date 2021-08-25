package blockchain.controllers

import blockchain.*
import blockchain.persistence.dao.NodeInfoDAO
import blockchain.persistence.tables.NodeInfoTable
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import newPersistence.ResultSet
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

/**
 * Gerenciadoe de nós da rede blockchain
 */
class NodeManager (
    val nodes:MutableList<NodeInfo> = mutableListOf<NodeInfo>(),
) {
    val nodeInfoDAO = NodeInfoDAO()
    var transactionQueue: Queue<Transaction> = LinkedList<Transaction>()

    init {
        transaction {
            SchemaUtils.create(
                NodeInfoTable
            )
        }
    }

    val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    fun addTransactionToQueue(transaction: Transaction) {
        transactionQueue.add(transaction)
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

    fun addNode(node: NodeInfo):NodeInfo {
        return nodeInfoDAO.insert(node)
    }

    fun getNode(id: UUID):NodeInfo? {
        return nodeInfoDAO.select(id)
    }

    fun getNodes(page: Int = 1):ResultSet<NodeInfo> {
        return nodeInfoDAO.selectAll(1)
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