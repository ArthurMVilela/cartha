package presentation

import blockchain.NodeInfo
import blockchain.persistence.dao.NodeInfoDAO
import java.util.*

class NodeManagerPresentationSetup {
    fun setupNodes() {
        val nodeInfoDAO = NodeInfoDAO()

        nodeInfoDAO.insert(
            NodeInfo(
                UUID.fromString(System.getenv("PRESENTATION_TEST_NODE_A_ID")!!),
                UUID.fromString(System.getenv("PRESENTATION_TEST_NOTARY_ID")!!),
                System.getenv("PRESENTATION_TEST_NODE_A_ADDRESS")!!
            )
        )
        nodeInfoDAO.insert(
            NodeInfo(
                UUID.fromString(System.getenv("PRESENTATION_TEST_NODE_B_ID")!!),
                UUID.fromString(System.getenv("PRESENTATION_TEST_NOTARY_ID")!!),
                System.getenv("PRESENTATION_TEST_NODE_B_ADDRESS")!!
            )
        )
    }
}