package ui.pages.authentication

import authentication.logging.AccessLog
import authentication.logging.AccessLogSearchFilter
import persistence.ResultSet
import ui.pages.Page
import ui.pages.PageBuilder
import ui.values.EnumMaps

class AccessLogsPageBuilder: PageBuilder() {
    override val page: Page = Page("accessLogs.ftl", mutableMapOf())

    init {
        page.data["actionTypes"] = EnumMaps.actionTypes
        page.data["subjects"] = EnumMaps.subjects
    }

    fun setErrorMessage(errorMessage:String?) {
        page.data["errorMessage"] = errorMessage
    }

    fun setResultSet(resultSet: ResultSet<AccessLog>) {
        page.data["searchResult"] = resultSet
        page.data["currentPage"] = resultSet.currentPage?:0
        page.data["numberOfPages"] = resultSet.numberOfPages?:0
    }

    fun setFilter(filter: AccessLogSearchFilter?) {
        page.data["filter"] = filter
    }
}