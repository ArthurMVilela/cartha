package ui.pages.blockchain

import blockchain.BlockInfo
import blockchain.NodeInfo
import persistence.ResultSet
import ui.pages.Page
import ui.pages.PageBuilder

class BlockchainBlocksPageBuilder: PageBuilder() {
    override val page: Page = Page("blockchainChain.ftl", mutableMapOf())

    fun setSetNodeInfo(nodeInfo:NodeInfo) {
        page.data["node"] = nodeInfo
    }

    fun setResultSet(resultSet: ResultSet<BlockInfo>?) {
        page.data["blocks"] = resultSet?.rows
        page.data["currentPage"] = resultSet?.currentPage?:0
        page.data["numberOfPages"] = resultSet?.numberOfPages?:0
    }
}