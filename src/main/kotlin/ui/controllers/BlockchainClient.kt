package ui.controllers

import blockchain.Block
import blockchain.BlockInfo
import blockchain.NodeInfo
import blockchain.Transaction
import blockchain.handlers.AddNodeRequest
import blockchain.handlers.CreateTransactionRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.features.*
import io.ktor.http.*
import persistence.ResultSet
import java.util.*

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

    suspend fun createNode(rb: AddNodeRequest): NodeInfo {
        val response: HttpResponse = try {
            client.post("$nodeManagerURL/nodes") {
                contentType(ContentType.Application.Json)
                body = rb
            }
        } catch (ex: Exception) {
            throw ex
        }

        return response.receive()
    }

    suspend fun createTransaction(rb: CreateTransactionRequest): Transaction {
        val response: HttpResponse = try {
            client.post("$nodeManagerURL/transactions") {
                contentType(ContentType.Application.Json)
                body = rb
            }
        } catch (ex: Exception) {
            throw ex
        }

        return response.receive()
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

    suspend fun getNode(id: UUID): NodeInfo {
        val response: HttpResponse = try {
            client.get("$nodeManagerURL/nodes/$id") {
            }
        } catch (ex: Exception) {
            throw ex
        }

        if (response.status == HttpStatusCode.NotFound) {
            throw NotFoundException("Página não encontrada")
        }

        return response.receive()
    }

    suspend fun getBlocks(nodeId: UUID, page: Int): ResultSet<BlockInfo> {
        val node = getNode(nodeId)

        val response: HttpResponse = try {
            client.get("${node.address}/blocks") {
                parameter("page", page)
            }
        } catch (ex: Exception) {
            throw ex
        }

        return response.receive()
    }

    suspend fun getBlock(nodeId: UUID, blockId: UUID): Block {
        val node = getNode(nodeId)

        val response: HttpResponse = try {
            client.get("${node.address}/blocks/${blockId}") {
            }
        } catch (ex: Exception) {
            throw ex
        }

        return response.receive()
    }

}