package blockchain.controllers

import blockchain.Block
import blockchain.NodeInfo
import blockchain.NodeStatus
import blockchain.Transaction
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class NodeClient {
    private val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 2000
        }

        expectSuccess = false
        HttpResponseValidator {
            validateResponse { response ->
                val statusCode = response.status.value
                when (statusCode) {
                    in 300..399 -> throw RedirectResponseException(response)
                    in 400..499 -> throw ClientRequestException(response)
                    in 500..599 -> throw ServerResponseException(response)
                }
            }
        }
    }

    suspend fun healthCheck(node: NodeInfo): NodeStatus {
        var status = NodeStatus.Unknown
        try {
            val request: HttpResponse = client.request("${node.address}/health") {
                method = HttpMethod.Get
            }

            status = when (request.status) {
                HttpStatusCode.OK -> NodeStatus.Online
                else -> NodeStatus.Offline
            }
        } catch (ex: Exception) {
            status = NodeStatus.Offline
        }

        return status
    }

    suspend fun createBlock(node: NodeInfo, transactions: List<Transaction>) : Block {
        val response: HttpResponse = client.post("${node.address}/blocks/new") {
            contentType(ContentType.Application.Json)
            body = transactions
        }

        return response.receive()
    }

    suspend fun sendBlock(node: NodeInfo, block: Block): Block {
        val response: HttpResponse = client.post("${node.address}/blocks") {
            contentType(ContentType.Application.Json)
            body = block
        }

        return response.receive()
    }
}