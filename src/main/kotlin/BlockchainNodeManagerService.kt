@file:JvmName("BlockchainNodeManagerService")

import blockchain.network.NodeManagerService
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
    val service = NodeManagerService()

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
        }
    }.start(true)
}