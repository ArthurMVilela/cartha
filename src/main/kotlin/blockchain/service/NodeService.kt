@file:JvmName("NodeService")

package blockchain.service

import blockchain.Blockchain
import blockchain.controllers.Node
import blockchain.handlers.NodeHandler
import blockchain.persistence.tables.BlockTable
import blockchain.persistence.tables.TransactionTable
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import serviceExceptions.BadRequestException
import java.util.*

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

        transaction {
            SchemaUtils.create(BlockTable, TransactionTable)
        }
    } catch (e:Exception) {
        println(e.message)
    }
    val nodeId = try {
        UUID.fromString(System.getenv("NODE_ID"))
    } catch (ex: Exception) {
        UUID.randomUUID()
    }
    val notaryId = try {
        UUID.fromString(System.getenv("NOTARY_ID"))
    } catch (ex: Exception) {
        UUID.randomUUID()
    }
    val nodeManagerURL = System.getenv("NODE_MANAGER_URL")?:""

    val node = Node(nodeId, Blockchain(), notaryId)

    val handler = NodeHandler(nodeManagerURL, node)

    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation)  {
            json()
        }
        install(StatusPages) {
            exception<NotFoundException> { cause ->
                call.respond(HttpStatusCode.NotFound, cause.message?:"")
            }
            exception<BadRequestException> { cause ->
                call.respond(HttpStatusCode.BadRequest, cause.message?:"")
            }
            exception<Exception> { cause ->
                call.respond(HttpStatusCode.InternalServerError)
            }
        }
        routing {
            get("/health") {
                handler.getHealthCheck(call)
            }
            get("/blocks/{id}") {
                handler.getBlock(call)
            }
            get("/blocks/last") {
                handler.getLast(call)
            }
            get("/blockchain") {
                handler.getBlockchain(call)
            }
            post("/blocks/new") {
                handler.createBlock(call)
            }
            get("/blocks") {
                handler.getBlocks(call)
            }
            post("/blocks") {
                handler.addBlock(call)
            }
        }
    }.start(true)
}