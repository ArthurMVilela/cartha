package ui.controllers

import blockchain.Block
import blockchain.BlockInfo
import blockchain.NodeInfo
import blockchain.Transaction
import blockchain.handlers.AddNodeRequest
import blockchain.handlers.CreateTransactionRequest
import persistence.ResultSet
import java.util.*

class BlockchainController {
    val nodeManagerURL = System.getenv("NODE_MANAGER_URL")?:throw Exception()
    val client = BlockchainClient(nodeManagerURL)

    suspend fun getNode(id: UUID): NodeInfo {
        return client.getNode(id)
    }

    suspend fun getNodes(page:Int):ResultSet<NodeInfo> {
        return client.getNodes(page)
    }

    suspend fun getBlocks(nodeId: UUID, page: Int): ResultSet<BlockInfo> {
        return client.getBlocks(nodeId, page)
    }

    suspend fun getBlock(nodeId: UUID, blockId: UUID): Block {
        return client.getBlock(nodeId, blockId)
    }

    suspend fun createNode(rb: AddNodeRequest) : NodeInfo {
        return client.createNode(rb)
    }

    suspend fun createTransaction(rb: CreateTransactionRequest):Transaction {
        return client.createTransaction(rb)
    }
}