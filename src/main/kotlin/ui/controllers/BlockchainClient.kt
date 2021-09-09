package ui.controllers

import authentication.logging.AccessLog
import blockchain.NodeInfo
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.get
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.features.*
import io.ktor.http.*
import newPersistence.ResultSet

class BlockchainClient(
    val nodeManagerURL:String
) {
    private val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
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

    suspend fun getNodes(page:Int):ResultSet<NodeInfo> {
        val response: HttpResponse = try {
            client.get("$nodeManagerURL/nodes") {
                parameter("page", page)
            }
        } catch (ex: Exception) {
            throw ex
        }

        if (response.status == HttpStatusCode.NotFound) {
            throw NotFoundException("Página não encontrada")
        }

        return response.receive()
    }
}