package authentication.handlers

import authentication.Subject
import authentication.controllers.AuthenticationController
import authentication.logging.AccessLogSearchFilter
import authentication.logging.Action
import authentication.logging.ActionType
import authentication.logging.exceptions.AccessLogNotFoundException
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import java.time.LocalDateTime
import java.util.*

class AccessLogHandler {
    private val authenticationController = AuthenticationController()
    suspend fun logAction(call: ApplicationCall) {
        val body = call.receive<AccessLogRequestBody>()

        val accessLog = authenticationController.logAction(body.sessionId, body.action, body.timestamp)

        call.respond(HttpStatusCode.OK, accessLog)
    }

    suspend fun getLog(call: ApplicationCall) {
        val id = call.parameters["id"]?:throw BadRequestException("Id não pode ser nula.")
        val uuid = UUID.fromString(id)
        val log = try {
            authenticationController.getAccessLog(uuid)
        } catch (ex: AccessLogNotFoundException) {
            throw NotFoundException(ex.message)
        }
        call.respond(HttpStatusCode.OK, log)
    }

    suspend fun getLogs(call: ApplicationCall) {
        val filter = call.receive<AccessLogSearchFilter>()
        val page = call.request.queryParameters["page"]?.toInt()?:1
        val logs = try {
            authenticationController.getAccessLogs(filter, page)
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw BadRequestException("Erro ao tentar aplicar filtro")
        }
        call.respond(HttpStatusCode.OK, logs)
    }
}