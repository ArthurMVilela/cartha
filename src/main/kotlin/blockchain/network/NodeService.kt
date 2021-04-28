package blockchain.network

import blockchain.Block
import blockchain.Blockchain
import blockchain.Transaction
import serviceExceptions.BadRequestException
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import java.time.LocalDateTime

class NodeService(val nodeManagerAddress:String, val node: Node) {

    suspend fun getBlock(call:ApplicationCall) {
        val id = call.parameters["id"]
        if (id.isNullOrBlank()) {
            throw BadRequestException("id não pode ser nula ou vázia")
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

        node.chain.addBlock(LocalDateTime.now(), transactions)
        call.respond(node.chain.getLast())
    }

    suspend fun createTransaction(call: ApplicationCall) {
        val transaction = call.receive<Transaction>()


    }
}