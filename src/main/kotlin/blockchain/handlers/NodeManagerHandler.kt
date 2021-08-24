package blockchain.handlers

import blockchain.Transaction
import blockchain.controllers.NodeManager
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.request.ContentTransformationException
import io.ktor.response.*
import serviceExceptions.BadRequestException
import java.util.*

class NodeManagerHandler {
    val nodeManager = NodeManager()

    suspend fun createTransaction(call:ApplicationCall) {
        val transaction = try {
            call.receive<Transaction>()
        } catch (e:ContentTransformationException) {
            throw BadRequestException("conteudo da requisição é inválido")
        }

        nodeManager.addTransactionToQueue(Transaction(transaction.timestamp, transaction.documentId, transaction.documentHash, transaction.type))

        call.respond(HttpStatusCode.Created, "Transação adicionada com sucesso")
    }

    suspend fun getTransactions(call: ApplicationCall) {
        call.respond(nodeManager.transactionQueue.toList())
    }

    suspend fun getNodes(call: ApplicationCall) {
        call.respond(nodeManager.nodes)
    }

    suspend fun getNode(call: ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw BadRequestException("ID inválida ou nula.")
        }

        val node = try {
            nodeManager.nodes.first { node -> node.nodeId == id }
        } catch (ex: Exception) {
            throw NotFoundException("Nó não encontrado.")
        }

        call.respond(node)
    }

//    suspend fun transmitBlock(call: ApplicationCall) {
//        val block = call.receive<Block>()
//
//        nodeManager.transmitBlock(block)
//
//        call.respond("ok")
//    }
}