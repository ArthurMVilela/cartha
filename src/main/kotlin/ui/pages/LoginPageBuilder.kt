package ui.pages

import io.ktor.http.*

class LoginPageBuilder:PageBuilder() {
    override val page: Page = Page("login.ftl", mutableMapOf())

    fun setErrorMessage(errorMessage:String) {
        page.data["errorMessage"] = errorMessage
    }
}