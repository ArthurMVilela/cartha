package ui.pages

import io.ktor.http.*

class ErrorPageBuilder:PageBuilder() {
    override val page: Page = Page("error.ftl", mutableMapOf())

    fun setError(code: HttpStatusCode, title: String, desc: String) {
        page.data["code"] = code.value
        page.data["errorTitle"] = title
        page.data["errorDesc"] = desc
    }
}