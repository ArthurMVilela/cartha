@file:JvmName("NodeManagerService")

package blockchain.service

import blockchain.handlers.NodeManagerHandler
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.slf4j.event.Level
import serviceExceptions.BadRequestException

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
        e.printStackTrace()
    }

    val service = NodeManagerHandler()

    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation)  {
            json()
        }
        install(StatusPages) {
            exception<BadRequestException> { cause ->
                call.respond(HttpStatusCode.BadRequest, cause.message!!)
            }
            exception<NotFoundException> { cause ->
                call.respond(HttpStatusCode.NotFound, cause.message!!)
            }
            exception<Exception> { cause ->
                cause.printStackTrace()
                call.respond(HttpStatusCode.InternalServerError)
            }
        }
        install(CallLogging) {
            level = Level.INFO
            format { call ->
                val method = call.request.httpMethod
                val status = call.response.status()

                val uri = call.request.uri

                "${status?.value} | ${method.value} $uri"
            }
        }
        routing {
            get("/transactions/{id}") {
                service.getTransaction(call)
            }
            get("/transactions/pending") {
                service.getPendingTransactions(call)
            }
            get("/transactions/document/{id}") {
                service.getTransactionByDocument(call)
            }
            post("/transactions") {
                service.createTransaction(call)
            }
            get("/nodes") {
                service.getNodes(call)
            }
            get("/nodes/{id}") {
                service.getNode(call)
            }
            get("/nodes/notary/{id}") {
                service.getNodeByNotary(call)
            }
            post("/nodes") {
                service.postNode(call)
            }
        }
    }.start(true)
}