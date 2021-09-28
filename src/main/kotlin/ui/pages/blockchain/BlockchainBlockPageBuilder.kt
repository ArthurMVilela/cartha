package ui.pages.blockchain

import blockchain.Block
import ui.pages.Page
import ui.pages.PageBuilder

class BlockchainBlockPageBuilder: PageBuilder() {
    override val page: Page = Page("blockchainBlock.ftl", mutableMapOf())

    fun setBlock(block: Block) {
        page.data["block"] = block
    }
}