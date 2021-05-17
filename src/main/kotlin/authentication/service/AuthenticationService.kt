@file:JvmName("AuthenticationService")

package authentication.service

import authentication.handlers.UserHandler
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    val userHandler = UserHandler()
    embeddedServer(Netty, port=8080) {
        install(ContentNegotiation)  {
            json()
        }
        install(StatusPages) {
            exception<Throwable> { cause ->
                call.respond(HttpStatusCode.InternalServerError, cause.message?:"Erro inexperado ocorreu.")
            }
            exception<BadRequestException> { cause ->
                call.respond(HttpStatusCode.BadRequest, cause.message?:"Erro inexperado ocorreu.")
            }
        }
        routing {
            post("/users") {
                userHandler.createUser(call)
            }
        }
    }.start(wait = true)
}
