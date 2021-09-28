package ui.pages.document

import document.Notary
import persistence.ResultSet
import ui.pages.Page
import ui.pages.PageBuilder

class NotariesPageBuilder: PageBuilder() {
    override val page: Page = Page("documentNotaries.ftl", mutableMapOf())

    fun setResultSet(resultSet: ResultSet<Notary>?) {
        page.data["notaries"] = resultSet?.rows
        page.data["currentPage"] = resultSet?.currentPage?:0
        page.data["numberOfPages"] = resultSet?.numberOfPages?:0
    }
}