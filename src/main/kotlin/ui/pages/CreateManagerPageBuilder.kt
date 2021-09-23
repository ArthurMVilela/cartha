package ui.pages

import ui.values.EnumMaps
import java.util.*

class CreateManagerPageBuilder: PageBuilder() {
    override val page: Page = Page("accountCreateManager.ftl", mutableMapOf())

    init {
        setEnums()
    }

    fun setNotaryId(notaryId: UUID) {
        page.data["notaryId"] = notaryId
    }

    private fun setEnums() {
        page.data["sex"] = EnumMaps.sex
    }
}