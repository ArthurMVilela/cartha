package blockchain.handlers

import blockchain.AddNodeRequest
import blockchain.CreateTransactionRequest
import blockchain.NodeInfo
import blockchain.Transaction
import blockchain.controllers.NodeManager
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.request.ContentTransformationException
import io.ktor.response.*
import serviceExceptions.BadRequestException
import java.time.LocalDateTime
import java.util.*

class NodeManagerHandler {
    val nodeManager = NodeManager()

    suspend fun createTransaction(call:ApplicationCall) {
        val transaction = try {
            call.receive<CreateTransactionRequest>()
        } catch (e:ContentTransformationException) {
            throw BadRequestException("conteudo da requisição é inválido")
        }

        nodeManager.addTransactionToQueue(Transaction(LocalDateTime.now(), transaction.documentId, transaction.documentHash, transaction.type))

        call.respond(HttpStatusCode.Created, "Transação adicionada com sucesso")
    }

    suspend fun getTransactions(call: ApplicationCall) {
        call.respond(nodeManager.transactionQueue.toList())
    }

    suspend fun getNodes(call: ApplicationCall) {
        val nodes = nodeManager.getNodes()

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

    suspend fun postNode(call: ApplicationCall) {
        val request = try {
            call.receive<AddNodeRequest>()
        } catch (ex: Exception) {
            throw BadRequestException("Request inválido.")
        }

        val node = NodeInfo(request.notaryId, request.address)

        val created = try {
            nodeManager.addNode(node)
        } catch (ex: Exception) {
            throw ex
        }

        call.respond(HttpStatusCode.Created, created)
    }

//    suspend fun transmitBlock(call: ApplicationCall) {
//        val block = call.receive<Block>()
//
//        nodeManager.transmitBlock(block)
//
//        call.respond("ok")
//    }
}