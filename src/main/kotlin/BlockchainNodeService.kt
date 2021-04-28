@file:JvmName("BlockchainNodeService")

import blockchain.network.NodeService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import serviceExceptions.BadRequestException

fun main() {
    val nodeService = NodeService("111111")

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
            get("/blocks/{id}") {
                nodeService.getBlock(call)
            }
            get("/blocks/last") {
                nodeService.getLast(call)
            }
            get("/blockchain") {
                nodeService.getBlockchain(call)
            }
        }
    }.start(true)
}