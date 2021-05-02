@file:JvmName("BlockchainNodeService")

import blockchain.network.Node
import blockchain.network.NodeService
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.runBlocking
import serviceExceptions.BadRequestException

fun main() {
    val nodeId = System.getenv("NODE_ID")
    val nodeManagerURL = System.getenv("NODE_MANAGER_URL")

    val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    var node:Node
    runBlocking {
        node = client.get {
            url("$nodeManagerURL/nodes/$nodeId")
        }
    }

    val nodeService = NodeService(nodeManagerURL, node)

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
            post("/blocks/new") {
                nodeService.createBlock(call)
            }
            post("/blocks") {
                nodeService.addBlock(call)
            }
        }
    }.start(true)
}