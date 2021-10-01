package ui.pages.blockchain

import ui.pages.Page
import ui.pages.PageBuilder
import java.util.*

class CreateNodePageBuilder: PageBuilder() {
    override val page: Page = Page("blockchainCreateNode.ftl", mutableMapOf())

    fun setNotaryId(id: UUID) {
        page.data["notaryId"] = id
    }
}