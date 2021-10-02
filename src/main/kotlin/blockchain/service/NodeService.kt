@file:JvmName("NodeService")

package blockchain.service

import blockchain.Blockchain
import blockchain.controllers.Node
import blockchain.handlers.NodeHandler
import blockchain.persistence.DatabaseInitializer
import blockchain.persistence.tables.BlockTable
import blockchain.persistence.tables.TransactionTable
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
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.event.Level
import serviceExceptions.BadRequestException
import java.util.*
import kotlin.system.exitProcess

fun main() {
    try {
        DatabaseInitializer.loadConfigurations()
        DatabaseInitializer.initialize()
    } catch (e:Exception) {
        e.printStackTrace()
        exitProcess(1)
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