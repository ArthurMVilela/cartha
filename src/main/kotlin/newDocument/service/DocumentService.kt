@file:JvmName("DocumentService")

package newDocument.service

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import newDocument.handlers.notary.NotaryHandler
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager

fun main() {
    try {
        val host = System.getenv("DATABASE_HOST")
        val port = System.getenv("DATABASE_PORT")
        val database = System.getenv("DATABASE_NAME")
        val user = System.getenv("DATABASE_USER")
        val password = System.getenv("DATABASE_PASSWORD")
        val url = "jdbc:mysql://$host:$port/$database?verifyServerCertificate=false&useSSL=true"
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

    val notaryHandler = NotaryHandler()

    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            json()
        }
        install(StatusPages) {
            exception<Throwable> { cause ->
                call.respond(HttpStatusCode.InternalServerError, "Erro inexperado ocorreu.")
            }
            exception<BadRequestException> { cause ->
                call.respond(HttpStatusCode.BadRequest, cause.message?:"Erro inexperado ocorreu.")
            }
            exception<NotFoundException> { cause ->
                call.respond(HttpStatusCode.NotFound, cause.message?:"Erro inexperado ocorreu.")
            }
        }
        routing {
            route("/notary") {
                post("") {
                    notaryHandler.createNotary(call)
                }
                get("/{id}") {
                    notaryHandler.getNotary(call)
                }
                get("/cnpj/{cnpj}") {
                    notaryHandler.getNotary(call)
                }
            }
        }
    }.start(true)
}