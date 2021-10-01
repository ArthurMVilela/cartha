package ui.pages

import ui.values.EnumMaps
import java.util.*

class CreateOfficialPageBuilder: PageBuilder() {
    override val page: Page = Page("accountCreateOfficial.ftl", mutableMapOf())

    init {
        setEnums()
    }

    fun setNotaryId(notaryId: UUID) {
        page.data["notaryId"] = notaryId
    }

    fun setErrorMessage(message: String?) {
        page.data["errorMessage"] = message
    }

    private fun setEnums() {
        page.data["sex"] = EnumMaps.sex
    }
}