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
import ui.features.getUserRole
import ui.features.logAction
import ui.pages.AccessLogPageBuilder
import ui.pages.AccessLogsPageBuilder
import ui.util.Util
import ui.values.EnumMaps
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AccessLogsHandlers {
    private val authController = AuthenticationController()

    suspend fun getLogs(call:ApplicationCall) {
        val pageNumber = call.request.queryParameters["page"]?.toInt()?:1
        val filter = call.sessions.get<AccessLogSearchFilter>()

        val searchResult = authController.getAccessLogs(
            filter?:AccessLogSearchFilter(null, null, null, null, null, null),
            pageNumber
        )

        val pageBuilder = AccessLogsPageBuilder()

        pageBuilder.setupMenu(call.getUserRole())
        pageBuilder.setResultSet(searchResult)
        pageBuilder.setFilter(filter)

        call.logAction(ActionType.SeeLogs, Subject.UserAccount, null)

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    suspend fun postGetLogs(call: ApplicationCall) {
        val parameters = call.receiveParameters()
        val pageNumber = call.request.queryParameters["page"]?.toInt()?:1

        val filter = parseFilterForm(parameters)
        call.sessions.set("accessLogSearchFilter", filter)
        val searchResult = authController.getAccessLogs(
            filter,
            pageNumber
        )

        val pageBuilder = AccessLogsPageBuilder()

        pageBuilder.setupMenu(call.getUserRole())
        pageBuilder.setResultSet(searchResult)
        pageBuilder.setFilter(filter)

        call.logAction(ActionType.SeeLogs, Subject.UserAccount, null)

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    suspend fun getLog(call: ApplicationCall) {

        val logId = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw BadRequestException("Id não válida", ex)
        }

        val log = try {
            authController.getAccessLog(logId)
        } catch (ex: Exception) {
            throw NotFoundException()
        }

        val pageBuilder = AccessLogPageBuilder()

        pageBuilder.setupMenu(call.getUserRole())
        pageBuilder.setLog(log)

        call.logAction( ActionType.SeeLog, Subject.UserAccount, log.id)

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
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