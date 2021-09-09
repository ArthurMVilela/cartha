package ui.pages

import blockchain.Block

class BlockchainBlockPageBuilder:PageBuilder() {
    override val page: Page = Page("blockchainBlock.ftl", mutableMapOf())

    fun setBlock(block: Block) {
        page.data["block"] = block
    }
}