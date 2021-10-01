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
        val host = System.getenv("DATABASE_HOST")?:throw IllegalArgumentException("Necessário expecificar host do DB")
        val port = System.getenv("DATABASE_PORT")?:throw IllegalArgumentException("Necessário expecificar porta do DB")
        val database = System.getenv("DATABASE_NAME")?:throw IllegalArgumentException("Necessário expecificar nome do DB")
        val user = System.getenv("DATABASE_USER")?:throw IllegalArgumentException("Necessário expecificar usuário do DB")
        val password = System.getenv("DATABASE_PASSWORD")?:throw IllegalArgumentException("Necessário expecificar senha do DB")
        val url = "jdbc:mysql://$host:$port/$database?verifyServerCertificate=false&useSSL=false&allowPublicKeyRetrieval=true"
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
            get("/access_logs") {
                accessLogHandler.getLogs(call)
            }
            get("/access_logs/{id}") {
                accessLogHandler.getLog(call)
            }
            get("/user/email/{email}") {
                userHandler.getAccount(call)
            }
            get("/user/cpf/{cpf}") {
                userHandler.getAccountByCpf(call)
            }
        }
    }.start(wait = true)
}
