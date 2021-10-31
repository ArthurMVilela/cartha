package ui.pages.document.civilRegistry

import document.Notary
import document.civilRegistry.birth.BirthCertificate
import document.person.Official
import ui.pages.Page
import ui.pages.PageBuilder
import ui.util.Util

class BirthCertificatePrintPage: PageBuilder() {
    override val page: Page = Page("documentBirthCertificatePrint.ftl", mutableMapOf())

    fun setBirthCertificate(bc: BirthCertificate) {
        page.data["birthCertificate"] = bc
        page.data["fullStringBirthday"] = Util.dateFullString(bc.dateTimeOfBirth.toLocalDate())
        page.data["fullStringRegistryDay"] = Util.dateFullString(bc.dateOfRegistry)
    }

    fun setNotary(notary: Notary) {
        page.data["notary"] = notary
    }

    fun setOfficial(official: Official) {
        page.data["official"] = official
    }
}