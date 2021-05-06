@file:JvmName("AuthenticationService")

import authentication.services.AuthenticationService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import kotlin.system.exitProcess

fun main() {

    try {
        val host = System.getenv("DATABASE_HOST")
        val port = System.getenv("DATABASE_PORT")
        val database = System.getenv("DATABASE_NAME")
        val user = System.getenv("DATABASE_USER")
        val password = System.getenv("DATABASE_PASSWORD")
        val url = "jdbc:mysql://$host:$port/$database?verifyServerCertificate=false&useSSL=true"
        println(url)
        val db = Database.connect(
            url = url,
            driver = "com.mysql.jdbc.Driver",
            user = user,
            password = password,
        )

        TransactionManager.defaultDatabase = db
    } catch (e:Exception) {
        println(e.message)
        exitProcess(2)
    }

    val service = AuthenticationService()
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            json()
        }
        install(StatusPages) {
            exception<BadRequestException> { cause ->
                call.respond(HttpStatusCode.BadRequest, cause.message?:"")
            }
            exception<NotFoundException> { cause ->
                call.respond(HttpStatusCode.NotFound, cause.message?:"")
            }
            exception<ExposedSQLException> { cause ->
                call.respond(HttpStatusCode.BadRequest, cause.message?:"")
            }
        }
        routing {
            post("/user/client") {
                service.createClientUser(call)
            }
            post("/user/official") {
                service.createOfficialUser(call)
            }
            post("/user/manager") {
                service.createManagerUser(call)
            }
            post("/user/sysadmin") {
                service.createSysAdmin(call)
            }
            post("/login") {
                service.login(call)
            }
            get("/session/{id}") {
                service.getSession(call)
            }
            post("/logout/{id}") {
                service.logout(call)
            }


        }
    }.start(true)

}