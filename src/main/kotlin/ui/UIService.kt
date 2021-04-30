package ui

import blockchain.Blockchain
import blockchain.Transaction
import blockchain.TransactionType
import blockchain.network.Node
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.freemarker.*
import io.ktor.response.*
import io.ktor.util.*
import io.ktor.util.url
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import kotlin.text.get

class UIService {
    val nodeManagerURL = "http://node_manager:8080"
    val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    suspend fun getBlockChain(call:ApplicationCall) {
        val notaryId = call.parameters["notaryId"]
        val selectedId:String

        val nodes:List<Node>
        runBlocking {
            nodes = client.get {
                url("$nodeManagerURL/nodes")
            }
        }

        if (notaryId.isNullOrEmpty()) {
            selectedId = nodes.first().id!!
        } else {
            selectedId = notaryId
        }

        val selectedNode = nodes.first { node -> node.id == selectedId }
        val blockchain:Blockchain

        runBlocking {
            blockchain = client.get {
                url("${selectedNode.address}/blockchain")
            }
        }


        val data = mapOf(
            "nodes" to nodes,
            "selected" to selectedId,
            "blockchain" to blockchain,
        )

        call.respond(FreeMarkerContent("blockchain.ftl", data))
    }
}