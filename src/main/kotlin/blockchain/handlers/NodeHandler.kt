package blockchain.handlers

import blockchain.Block
import blockchain.Transaction
import blockchain.controllers.Node
import serviceExceptions.BadRequestException
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.request.*
import io.ktor.response.*
import java.time.LocalDateTime
import java.util.*

class NodeHandler(val nodeManagerAddress:String, val node: Node) {

    val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    suspend fun getBlock(call:ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw BadRequestException("id não válida")
        }

        call.respond(node.chain.getBlock(id))
    }

    suspend fun getLast(call:ApplicationCall) {
        val block = node.chain.getLast()

        call.respond(block)
    }

    suspend fun getBlockchain(call: ApplicationCall) {
        call.respond(node.chain)
    }

    suspend fun createBlock(call: ApplicationCall) {
        val transactions = call.receive<List<Transaction>>()
        if (transactions.isNullOrEmpty()) {
            throw BadRequestException("lista de transações vázia")
        }

        node.chain.addBlock(LocalDateTime.now(), transactions, node.id!!)
        call.respond(node.chain.getLast())
    }

    suspend fun addBlock(call: ApplicationCall) {
        val block = try {
            call.receive<Block>()
        } catch (ex: Exception) {
            throw BadRequestException("Corpo da requisição inválida")
        }

        node.chain.addBlock(block)

        call.respond(block)
    }

    suspend fun createTransaction(call: ApplicationCall) {
        val transaction = call.receive<Transaction>()


    }
}