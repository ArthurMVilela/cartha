package ui.pages

import authentication.Role
import ui.controllers.AuthenticationController
import ui.features.UserSessionCookie
import ui.values.Menus

open class PageBuilder {
    open val page: Page = Page("", mutableMapOf())
    protected val authController = AuthenticationController()

    fun setupMenu(role:Role?) {
        when(role) {
            null -> page.data["menu"] = Menus.anonimous
            Role.Manager -> page.data["menu"] = Menus.manager
            Role.Official -> page.data["menu"] = Menus.official
            Role.SysAdmin -> page.data["menu"] = Menus.sysAdmin
            Role.Client -> page.data["menu"] = Menus.client
        }
    }

    fun build(): Page {
        return page
    }
}