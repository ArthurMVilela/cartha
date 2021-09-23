package ui.pages.document

import document.Notary
import ui.pages.Page
import ui.pages.PageBuilder

class NotaryPageBuilder: PageBuilder() {
    override val page: Page = Page("documentNotary.ftl", mutableMapOf())

    fun setUpNotary(notary: Notary) {
        page.data["notary"] = notary
    }
}