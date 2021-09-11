package ui.pages

import authentication.logging.AccessLog
import authentication.logging.AccessLogSearchFilter
import newPersistence.ResultSet
import ui.values.EnumMaps

class AccessLogsPageBuilder:PageBuilder() {
    override val page: Page = Page("accessLogs.ftl", mutableMapOf())

    init {
        page.data["actionTypes"] = EnumMaps.actionTypes
        page.data["subjects"] = EnumMaps.subjects
    }

    fun setResultSet(resultSet: ResultSet<AccessLog>) {
        page.data["searchResult"] = resultSet
    }

    fun setFilter(filter: AccessLogSearchFilter?) {
        page.data["filter"] = filter
    }
}