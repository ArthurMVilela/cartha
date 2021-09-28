package ui.pages.blockchain

import blockchain.NodeInfo
import persistence.ResultSet
import ui.pages.Page
import ui.pages.PageBuilder

class BlockchainNodesPageBuilder: PageBuilder() {
    override val page: Page = Page("blockchainNodes.ftl", mutableMapOf())

    fun setResultSet(nodeResultSet: ResultSet<NodeInfo>) {
        page.data["nodes"] = nodeResultSet.rows
        page.data["currentPage"] = nodeResultSet.currentPage
        page.data["numberOfPages"] = nodeResultSet.numberOfPages
    }
}