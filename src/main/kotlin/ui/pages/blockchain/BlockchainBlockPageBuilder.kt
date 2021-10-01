package ui.pages.blockchain

import blockchain.Block
import ui.pages.Page
import ui.pages.PageBuilder
import java.util.*

class BlockchainBlockPageBuilder: PageBuilder() {
    override val page: Page = Page("blockchainBlock.ftl", mutableMapOf())

    fun setNodeId(id: UUID) {
        page.data["nodeId"] = id
    }

    fun setBlock(block: Block) {
        page.data["block"] = block
    }
}