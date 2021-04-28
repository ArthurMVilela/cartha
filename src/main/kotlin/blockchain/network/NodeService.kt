package blockchain.network

import serviceExceptions.BadRequestException
import io.ktor.application.*
import io.ktor.response.*

class NodeService(notaryId:String) {
    val node = Node(notaryId)

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
}