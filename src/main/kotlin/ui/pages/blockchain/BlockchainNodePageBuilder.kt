package ui.pages.blockchain

import blockchain.NodeInfo
import ui.pages.Page
import ui.pages.PageBuilder

class BlockchainNodePageBuilder: PageBuilder() {
    override val page: Page = Page("blockchainNode.ftl", mutableMapOf())

    fun setNode(node: NodeInfo) {
        page.data["node"] = node
    }
}