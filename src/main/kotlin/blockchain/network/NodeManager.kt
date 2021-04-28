package blockchain.network

import blockchain.Block
import blockchain.Config
import blockchain.Transaction
import java.util.*

/**
 * Gerenciadoe de nós da rede blockchain
 */
class NodeManager (
    val nodes:MutableList<Node> = mutableListOf<Node>(),
) {
    private var transactionQueue: Queue<Transaction> = LinkedList<Transaction>()

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