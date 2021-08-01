package ui.handlers

import authentication.Role
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.sessions.*
import ui.controllers.AuthenticationController
import ui.features.UserSessionCookie
import ui.util.Util

class MainPageHandler {
    private val authController = AuthenticationController()
    /**
     * Recebe uma chamada da aplicação e retorna a tela principal
     *
     * @param call          chamada de aplicação
     */
    suspend fun mainPage(call: ApplicationCall) {
        val data = mutableMapOf<String, Any?>()
        val sessionCookie = call.sessions.get<UserSessionCookie>()
        if (sessionCookie != null && authController.isSessionValid(sessionCookie)) {
            try {
                Util.addMenuToLayoutMap(data, authController.getUserRole(sessionCookie))
            }catch (ex: Exception) {
                Util.addMenuToLayoutMap(data, null)
            }
        } else {
            Util.addMenuToLayoutMap(data, null)
        }

        call.respond(HttpStatusCode.OK, FreeMarkerContent("main.ftl", data))
    }
}