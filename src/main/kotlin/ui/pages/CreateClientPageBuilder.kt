package ui.pages

import ui.values.EnumMaps

class CreateClientPageBuilder: PageBuilder() {
    override val page: Page = Page("accountCreateClient.ftl", mutableMapOf())

    init {
        setEnums()
    }

    fun setErrorMessage(errorMessage: String?) {
        page.data["errorMessage"] = errorMessage
    }

    private fun setEnums() {
        page.data["sex"] = EnumMaps.sex
        page.data["color"] = EnumMaps.color
        page.data["civilStatus"] = EnumMaps.civilStatus
        page.data["months"] = EnumMaps.month
    }
}