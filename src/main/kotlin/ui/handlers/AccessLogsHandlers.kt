package ui.handlers

import authentication.logging.AccessLogSearchFilter
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.sessions.*
import ui.controllers.AuthenticationController
import ui.features.UserSessionCookie
import ui.util.Util

class AccessLogsHandlers {
    private val authController = AuthenticationController()

    suspend fun getLogs(call:ApplicationCall) {
        val data = mutableMapOf<String, Any?>()
        val page = call.request.queryParameters["page"]?.toInt()?:1
        println(page)
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

        val searchResult = authController.getAccessLogs(
            AccessLogSearchFilter(null, null, null, null, null, null),
            page
        )

        data["searchResult"] = searchResult

        call.respond(HttpStatusCode.OK, FreeMarkerContent("accessLogs.ftl", data))
    }
}