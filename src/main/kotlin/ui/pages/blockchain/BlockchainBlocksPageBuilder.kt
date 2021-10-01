package ui.pages.blockchain

import blockchain.BlockInfo
import blockchain.NodeInfo
import persistence.ResultSet
import ui.pages.Page
import ui.pages.PageBuilder
import java.util.*

class BlockchainBlocksPageBuilder: PageBuilder() {
    override val page: Page = Page("blockchainChain.ftl", mutableMapOf())

    fun setSetNodeInfo(nodeId: UUID) {
        page.data["nodeId"] = nodeId
    }

    fun setResultSet(resultSet: ResultSet<BlockInfo>?) {
        page.data["blocks"] = resultSet?.rows
        page.data["currentPage"] = resultSet?.currentPage?:0
        page.data["numberOfPages"] = resultSet?.numberOfPages?:0
    }
}