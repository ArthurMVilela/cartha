package ui.pages.document

import ui.pages.Page
import ui.pages.PageBuilder

class CreateNotaryPageBuilder: PageBuilder() {
    override val page: Page = Page("documentCreateNotary.ftl", mutableMapOf())
}