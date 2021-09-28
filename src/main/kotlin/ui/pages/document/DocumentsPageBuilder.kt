package ui.pages.document

import document.civilRegistry.birth.BirthCertificate
import ui.pages.Page
import ui.pages.PageBuilder

class DocumentsPageBuilder: PageBuilder() {
    override val page: Page = Page("document.ftl", mutableMapOf())

    fun setClientBirthCertificate(bc: BirthCertificate?) {
        page.data["clientBc"] = bc
    }

    fun setAffiliationsBirthCertificate(list: List<BirthCertificate>) {
        page.data["affiliationsBcs"] = list
    }
}