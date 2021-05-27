@file:JvmName("AuthenticationService")

package authentication.service

import authentication.handlers.AccessLogHandler
import authentication.handlers.UserHandler
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager

fun main() {
    try {
        val host = "localhost"
        val port = 3306
        val database = "authentication_db"
        val user = "root"
        val password = "test"
        val url = "jdbc:mysql://$host:$port/$database?verifyServerCertificate=false&useSSL=false"
        val db = Database.connect(
            url = url,
            driver = "com.mysql.jdbc.Driver",
            user = user,
            password = password,
        )

        TransactionManager.defaultDatabase = db
    } catch (e:Exception) {
        println(e.message)
    }
    val userHandler = UserHandler()
    val accessLogHandler = AccessLogHandler()
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
            post("/login") {
                userHandler.login(call)
            }
            get("/session/{id}") {
                userHandler.getSession(call)
            }
            post("/logout/{id}") {
                userHandler.logout(call)
            }
            post("/access_logs") {
                accessLogHandler.logAction(call)
            }
        }
    }.start(wait = true)
}
