package blockchain.controllers

import blockchain.NodeInfo
import blockchain.NodeStatus
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.get
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
}