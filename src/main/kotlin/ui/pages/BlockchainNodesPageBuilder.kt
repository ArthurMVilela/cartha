package ui.pages

import blockchain.NodeInfo
import persistence.ResultSet

class BlockchainNodesPageBuilder:PageBuilder() {
    override val page: Page = Page("blockchainNodes.ftl", mutableMapOf())

    fun setResultSet(nodeResultSet: ResultSet<NodeInfo>) {
        page.data["nodes"] = nodeResultSet.rows
        page.data["currentPage"] = nodeResultSet.currentPage
        page.data["numberOfPages"] = nodeResultSet.numberOfPages
    }
}