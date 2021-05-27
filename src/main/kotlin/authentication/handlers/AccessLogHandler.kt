package authentication.handlers

import authentication.Subject
import authentication.controllers.AuthenticationController
import authentication.logging.Action
import authentication.logging.ActionType
import io.ktor.application.*
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
}