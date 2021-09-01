@file:JvmName("NodeService")

package blockchain.service

import blockchain.Blockchain
import blockchain.controllers.Node
import blockchain.handlers.NodeHandler
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import serviceExceptions.BadRequestException
import java.util.*

fun main() {
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