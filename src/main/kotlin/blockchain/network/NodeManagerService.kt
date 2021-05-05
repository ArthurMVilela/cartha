package blockchain.network

import blockchain.Block
import blockchain.Transaction
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import serviceExceptions.BadRequestException

class NodeManagerService {
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
        val id = call.parameters["id"]
        val node = nodeManager.nodes.first { node -> node.id == id }
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