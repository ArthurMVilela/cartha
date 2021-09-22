package ui.pages.authentication

import authentication.logging.AccessLog
import ui.pages.Page
import ui.pages.PageBuilder

class AccessLogPageBuilder: PageBuilder() {
    override val page: Page = Page("accessLog.ftl", mutableMapOf())

    fun setLog(accessLog: AccessLog) {
        page.data["accessLog"] = accessLog
    }
}