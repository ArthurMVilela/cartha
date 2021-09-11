package ui.pages

import authentication.logging.AccessLog

class AccessLogPageBuilder:PageBuilder() {
    override val page: Page = Page("accessLog.ftl", mutableMapOf())

    fun setLog(accessLog: AccessLog) {
        page.data["accessLog"] = accessLog
    }
}