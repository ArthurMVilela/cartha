package ui.pages.blockchain

import ui.pages.Page
import ui.pages.PageBuilder

class BlockchainPageBuilder: PageBuilder() {
    override val page: Page = Page("blockchain.ftl", mutableMapOf())
}