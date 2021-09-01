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
        val host = System.getenv("DATABASE_HOST")?:"localhost"
        val port = System.getenv("DATABASE_PORT")?:"8080"
        val database = System.getenv("DATABASE_NAME")?:"node_db"
        val user = System.getenv("DATABASE_USER")?:"root"
        val password = System.getenv("DATABASE_PASSWORD")?:"test"
        val url = "jdbc:mysql://$host:$port/$database?verifyServerCertificate=false&useSSL=true"
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

    val nodeService = NodeHandler(nodeManagerURL, node)

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
            get("/health") {
                nodeService.getHealthCheck(call)
            }
            get("/blocks/{id}") {
                nodeService.getBlock(call)
            }
            get("/blocks/last") {
                nodeService.getLast(call)
            }
            get("/blockchain") {
                nodeService.getBlockchain(call)
            }
            post("/blocks/new") {
                nodeService.createBlock(call)
            }
            post("/blocks") {
                nodeService.addBlock(call)
            }
        }
    }.start(true)
}