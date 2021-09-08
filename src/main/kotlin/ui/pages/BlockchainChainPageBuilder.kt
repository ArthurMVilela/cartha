package ui.pages

import blockchain.BlockInfo
import newPersistence.ResultSet

class BlockchainChainPageBuilder:PageBuilder() {
    override val page: Page = Page("blockchainChain.ftl", mutableMapOf())

    fun setResultSet(resultSet: ResultSet<BlockInfo>) {
        page.data["blocks"] = resultSet.rows
        page.data["currentPage"] = resultSet.currentPage
        page.data["numberOfPages"] = resultSet.numberOfPages
    }
}