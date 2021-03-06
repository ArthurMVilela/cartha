package blockchain.handlers

import blockchain.NodeInfo
import blockchain.Transaction
import blockchain.controllers.NodeManager
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.request.ContentTransformationException
import io.ktor.response.*
import kotlinx.coroutines.runBlocking
import serviceExceptions.BadRequestException
import java.time.LocalDateTime
import java.util.*
import kotlin.concurrent.fixedRateTimer

class NodeManagerHandler {
    private val nodeManager = NodeManager()

    init {
        fixedRateTimer("healthCheck", false, initialDelay = 1000, period = 10*60*1000) {
            var nodes = nodeManager.getNodes(1)
            for (i in 1..nodes.numberOfPages) {
                nodes = nodeManager.getNodes(i)

                nodes.rows.forEach {
                    runBlocking {
                        nodeManager.checkNodeStatus(it)
                    }
                }
            }
        }
    }

    suspend fun createTransaction(call:ApplicationCall) {
        val transaction = try {
            call.receive<CreateTransactionRequest>()
        } catch (e:ContentTransformationException) {
            throw BadRequestException("conteudo da requisição é inválido")
        }


        val t = Transaction(LocalDateTime.now(), transaction.documentId, transaction.documentHash, transaction.type)
        nodeManager.addTransactionToQueue(t)

        call.respond(HttpStatusCode.Created, t)
    }

    suspend fun getTransaction(call: ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw BadRequestException("ID inválida ou nula.")
        }

        val t = nodeManager.getTransaction(id)?:throw NotFoundException()

        call.respond(HttpStatusCode.OK, t)
    }

    suspend fun getTransactionByDocument(call: ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw BadRequestException("ID inválida ou nula.")
        }

        val t = nodeManager.getTransactionByDocument(id)

        call.respond(HttpStatusCode.OK, t)
    }

    suspend fun getLastDocumentTransaction(call: ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw BadRequestException("ID inválida ou nula.")
        }

        val t = nodeManager.getLastDocumentTransaction(id)

        if (t != null) {
            call.respond(t)
        }


    }

    suspend fun getPendingTransactions(call: ApplicationCall) {
        val page = try {
            call.request.queryParameters["page"]?.toInt()?:1
        } catch (ex: Exception) {
            throw BadRequestException("Página inválida")
        }

        if (page < 1) {
            throw BadRequestException("Página inválida")
        }

        val transactions = nodeManager.getPendingTransactions(page)

        if (transactions.currentPage > transactions.numberOfPages) {
            throw NotFoundException("Página não encontrada.")
        }

        call.respond(transactions)
    }

    suspend fun getNodes(call: ApplicationCall) {
        val page = try {
            call.request.queryParameters["page"]?.toInt()?:1
        } catch (ex: Exception) {
            throw BadRequestException("Página inválida")
        }

        val nodes = nodeManager.getNodes(page)

        call.respond(nodes)
    }

    suspend fun getNode(call: ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw BadRequestException("ID inválida ou nula.")
        }

        val node = try {
            nodeManager.getNode(id)
        } catch (ex: Exception) {
            throw NotFoundException("Nó não encontrado.")
        }?:throw NotFoundException("Nó não encontrado.")

        call.respond(node)
    }

    suspend fun getNodeByNotary(call: ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw BadRequestException("ID inválida ou nula.")
        }

        val node = try {
            nodeManager.getNodeByNotary(id)
        } catch (ex: Exception) {
            throw NotFoundException("Nó não encontrado.")
        }?:throw NotFoundException("Nó não encontrado.")

        call.respond(node)
    }

    suspend fun postNode(call: ApplicationCall) {
        val request = try {
            call.receive<AddNodeRequest>()
        } catch (ex: Exception) {
            throw BadRequestException("Request inválido.")
        }

        val node = NodeInfo(request.notaryId, request.address, null)

        val created = try {
            nodeManager.addNode(node)
        } catch (ex: Exception) {
            throw ex
        }

        call.respond(HttpStatusCode.Created, created)
    }
}