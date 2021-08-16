package ui.handlers

import authentication.Subject
import authentication.logging.AccessLogSearchFilter
import authentication.logging.ActionType
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.sessions.*
import ui.controllers.AuthenticationController
import ui.features.UserSessionCookie
import ui.util.Util
import ui.values.EnumMaps
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AccessLogsHandlers {
    private val authController = AuthenticationController()

    suspend fun getLogs(call:ApplicationCall) {
        val data = mutableMapOf<String, Any?>()
        val page = call.request.queryParameters["page"]?.toInt()?:1
        val filter = call.sessions.get<AccessLogSearchFilter>()
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
            filter?:AccessLogSearchFilter(null, null, null, null, null, null),
            page
        )

        data["searchResult"] = searchResult
        data["actionTypes"] = EnumMaps.actionTypes
        data["subjects"] = EnumMaps.subjects
        data["filter"] = filter

        authController.logAction(sessionCookie!!, ActionType.SeeLogs, Subject.UserAccount, null)

        call.respond(HttpStatusCode.OK, FreeMarkerContent("accessLogs.ftl", data))
    }

    suspend fun postGetLogs(call: ApplicationCall) {
        val data = mutableMapOf<String, Any?>()
        val parameters = call.receiveParameters()
        val page = call.request.queryParameters["page"]?.toInt()?:1

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

        val filter = parseFilterForm(parameters)
        call.sessions.set("accessLogSearchFilter", filter)
        val searchResult = authController.getAccessLogs(
            filter,
            page
        )

        data["searchResult"] = searchResult
        data["actionTypes"] = EnumMaps.actionTypes
        data["subjects"] = EnumMaps.subjects
        data["filter"] = filter

        call.respond(HttpStatusCode.OK, FreeMarkerContent("accessLogs.ftl", data))
    }

    suspend fun getLog(call: ApplicationCall) {
        val logId = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw BadRequestException("Id não válida", ex)
        }

        val data = mutableMapOf<String, Any?>()
        val log = try {
            authController.getAccessLog(logId)
        } catch (ex: Exception) {
            throw NotFoundException()
        }

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

        data["log"] = log

        call.respond(HttpStatusCode.OK, FreeMarkerContent("accessLog.ftl", data))
    }

    private fun parseFilterForm(parameters: Parameters):AccessLogSearchFilter {
        val dateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        val userId = try {
            UUID.fromString(parameters["user-id"])
        } catch (ex: Exception) {
            null
        }
        val start = try {
            LocalDateTime.parse(parameters["start"], dateTimeFormat)
        } catch (ex: Exception) {
            null
        }
        val end = try {
            LocalDateTime.parse(parameters["end"], dateTimeFormat)
        } catch (ex: Exception) {
            null
        }
        val domainId = try {
            UUID.fromString(parameters["domain-id"])
        } catch (ex: Exception) {
            null
        }
        val actionType = try {
            ActionType.valueOf(parameters["action-type"]!!)
        } catch (ex: Exception) {
            null
        }
        val subject = try {
            Subject.valueOf(parameters["subject"]!!)
        } catch (ex: Exception) {
            null
        }

        return AccessLogSearchFilter(userId, start, end, domainId, actionType, subject)
    }
}