package ui.pages.document

import document.Notary
import document.person.Official
import ui.pages.Page
import ui.pages.PageBuilder

class NotaryPageBuilder: PageBuilder() {
    override val page: Page = Page("documentNotary.ftl", mutableMapOf())

    fun setUpNotary(notary: Notary) {
        page.data["notary"] = notary
    }

    fun setOfficials(officials: List<Official>) {
        page.data["officials"] = officials
    }
}