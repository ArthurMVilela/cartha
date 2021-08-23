package blockchain.controllers

import blockchain.Block
import blockchain.Blockchain
import blockchain.Config
import blockchain.Transaction
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import java.util.*

/**
 * Gerenciadoe de nós da rede blockchain
 */
class NodeManager (
    val nodes:MutableList<Node> = mutableListOf<Node>(),
) {
    var transactionQueue: Queue<Transaction> = LinkedList<Transaction>()

    init {
        nodes.add(Node(UUID.randomUUID(), Blockchain(), "1", "http://node_a:8080"))
        nodes.add(Node(UUID.randomUUID(), nodes[0].chain, "2", "http://node_b:8080"))
    }

    val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    fun addTransactionToQueue(transaction: Transaction) {
        transactionQueue.add(transaction)
        //transmitTransaction(transaction)
        if (transactionQueue.size >= Config.transactionAmount) {
            val pick = pickNodeToGenerateNextBlock()
            val transactions = mutableListOf<Transaction>()

            for (i in 0 until Config.transactionAmount) {
                transactions.add(transactionQueue.remove())
            }

            val block:Block
            runBlocking {
                block = requestBlock(transactions, pick)
            }

            runBlocking {
                transmitBlock(block)
            }
        }
    }

    fun addNode(node: Node) {
        TODO("Not implemented yet")
    }

    fun transmitTransaction(transaction: Transaction) {
        TODO("Not implemented yet")
    }

    suspend fun transmitBlock(block:Block) {
        nodes.forEach {
            println("${it.id} ${block.nodeId}")
            if (it.id != block.nodeId) {
                run {
                    val block = client.post<Block>("${it.address}/blocks") {
                        contentType(ContentType.Application.Json)
                        body = block
                    }
                }
            }
        }
    }

    fun pickNodeToGenerateNextBlock(): Node {
        val pick = nodes.random()

        return pick
    }

    suspend fun requestBlock(transactions: List<Transaction>, node: Node): Block {
        val block = client.post<Block>("${node.address}/blocks/new") {
            contentType(ContentType.Application.Json)
            body = transactions
        }

        return block
    }
}