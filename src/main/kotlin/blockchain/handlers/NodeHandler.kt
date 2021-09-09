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
import io.ktor.features.*
import io.ktor.http.*
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

    /**
     * Endpoint da aplicação para verificar saude do servidor, no momento
     * só retorna OK, caso haja problema ira retornar erro por cause do middleware
     * no futuro implementarei check mais complexo
     *
     * @param call      chamada de aplicação
     */
    suspend fun getHealthCheck(call: ApplicationCall) {
        call.respond(HttpStatusCode.OK)
    }

    suspend fun getBlock(call:ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw BadRequestException("id não válida")
        }

        val block = node.getBlock(id)?:throw NotFoundException()

        call.respond(block)
    }

    suspend fun getBlocks(call: ApplicationCall) {
        val page = try {
            call.request.queryParameters["page"]?.toInt()?:1
        } catch (ex: Exception) {
            throw BadRequestException("Página inválida")
        }

        if (page < 1) {
            throw BadRequestException("Página inválida")
        }

        val blocks = node.getBlocks(page)

        if (blocks.numberOfPages < page) {
            throw NotFoundException("Página não encontrada.")
        }

        call.respond(blocks)
    }

    suspend fun getLast(call:ApplicationCall) {
        val block = node.getLastBlock()?:throw NotFoundException()

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
        val last = node.chain.getLast()?:throw NotFoundException()
        call.respond(last)
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