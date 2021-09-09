package ui.controllers

import blockchain.NodeInfo
import newPersistence.ResultSet

class BlockchainController {
    val nodeManagerURL = System.getenv("NODE_MANAGER_URL")?:throw Exception()
    val client = BlockchainClient(nodeManagerURL)

    suspend fun getNodes(page:Int):ResultSet<NodeInfo> {
        return client.getNodes(page)
    }
}