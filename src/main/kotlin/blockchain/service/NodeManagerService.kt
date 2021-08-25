@file:JvmName("NodeManagerService")

package blockchain.service

import blockchain.handlers.NodeManagerHandler
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
import serviceExceptions.BadRequestException

fun main() {


    try {
        val host = System.getenv("DATABASE_HOST")?:"localhost"//throw IllegalArgumentException("Necessário expecificar host do DB")
        val port = System.getenv("DATABASE_PORT")?:"3306"//throw IllegalArgumentException("Necessário expecificar porta do DB")
        val database = System.getenv("DATABASE_NAME")?:"node_manager_db"//throw IllegalArgumentException("Necessário expecificar nome do DB")
        val user = System.getenv("DATABASE_USER")?:"root"//throw IllegalArgumentException("Necessário expecificar usuário do DB")
        val password = System.getenv("DATABASE_PASSWORD")?:"test"//throw IllegalArgumentException("Necessário expecificar senha do DB")
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

    val service = NodeManagerHandler()

    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation)  {
            json()
        }
        install(StatusPages) {
            exception<BadRequestException> { cause ->
                call.respond(HttpStatusCode.BadRequest, cause.message!!)
            }
        }
        routing {
            get("/transactions") {
                service.getTransactions(call)
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
            post("/nodes") {
                service.postNode(call)
            }
        }
    }.start(true)
}