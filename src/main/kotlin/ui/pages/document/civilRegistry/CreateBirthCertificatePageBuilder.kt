package ui.pages.document.civilRegistry

import ui.pages.Page
import ui.pages.PageBuilder
import ui.values.EnumMaps
import java.util.*

class CreateBirthCertificatePageBuilder:PageBuilder() {
    override val page: Page = Page("documentCreateBirthCertificate.ftl", mutableMapOf())

    init {
        setEnums()
    }

    private fun setEnums() {
        page.data["sex"] = EnumMaps.sex
        page.data["months"] = EnumMaps.month
        page.data["uf"] = EnumMaps.uf
        page.data["grandparentType"] = EnumMaps.grandparentType
    }

    fun setNotaryId(id: UUID) {
        page.data["notaryId"] = id
    }

    fun setOfficialId(id: UUID) {
        page.data["officialId"] = id
    }

}