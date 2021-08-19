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
import serviceExceptions.BadRequestException

fun main() {
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
//            post("/block") {
//                service.transmitBlock(call)
//            }
        }
    }.start(true)
}