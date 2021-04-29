package blockchain.network

import blockchain.Block
import blockchain.Blockchain
import blockchain.Config
import blockchain.Transaction
import java.util.*

/**
 * Gerenciadoe de nós da rede blockchain
 */
class NodeManager (
    val nodes:MutableList<Node> = mutableListOf<Node>(),
) {
    var transactionQueue: Queue<Transaction> = LinkedList<Transaction>()

    init {
        nodes.add(Node("1", Blockchain(), "1"))
        nodes.add(Node("2", nodes[0].chain, "2"))
    }

    fun addTransactionToQueue(transaction: Transaction) {
        transactionQueue.add(transaction)
        transmitTransaction(transaction)
        if (transactionQueue.size >= Config.transactionAmount) {
            pickNodeToGenerateNextBlock()
        }
    }

    fun addNode(node: Node) {
        TODO("Not implemented yet")
    }

    fun transmitTransaction(transaction: Transaction) {
        TODO("Not implemented yet")
    }

    fun transmitBlock(block:Block) {
        TODO("Not implemented yet")
    }

    fun pickNodeToGenerateNextBlock(){
        TODO("Not implemented yet")
    }
}