package ui.pages.document

import document.Notary
import persistence.ResultSet
import ui.pages.Page
import ui.pages.PageBuilder

class CivilRegistryPageBuilder: PageBuilder() {
    override val page: Page = Page("documentCivilRegistry.ftl", mutableMapOf())
}