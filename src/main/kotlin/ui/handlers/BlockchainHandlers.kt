package ui.handlers

import blockchain.Block
import blockchain.BlockInfo
import blockchain.NodeInfo
import blockchain.handlers.AddNodeRequest
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import persistence.ResultSet
import serviceExceptions.BadRequestException
import ui.controllers.AuthenticationController
import ui.controllers.BlockchainController
import ui.controllers.DocumentController
import ui.features.getUserRole
import ui.pages.blockchain.*
import java.time.LocalDateTime
import java.util.*

class BlockchainHandlers {
    private val blockchainController = BlockchainController()
    private val documentController = DocumentController()

    suspend fun getBlockchainPage(call: ApplicationCall) {
        val pageBuilder = BlockchainPageBuilder()

        pageBuilder.setupMenu(call.getUserRole())

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    suspend fun getNodesPage(call: ApplicationCall) {
        val pageNumber = try {
            call.request.queryParameters["page"]?.toInt()?:1
        } catch (ex: Exception) {
            throw BadRequestException("Página inválida")
        }

        if (pageNumber < 1) {
            throw BadRequestException("Página inválida")
        }

        val nodes = try {
            blockchainController.getNodes(pageNumber)
        } catch (ex: Exception) {
            throw NotFoundException("Página não encontrada.")
        }

        val pageBuilder = BlockchainNodesPageBuilder()

        pageBuilder.setupMenu(call.getUserRole())
        pageBuilder.setResultSet(nodes)

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    suspend fun getBlocksPage(call: ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["nodeId"])
        } catch (ex: Exception) {
            throw io.ktor.features.BadRequestException("Id inválida.")
        }

        val pageNumber = try {
            call.request.queryParameters["page"]?.toInt()?:1
        } catch (ex: Exception) {
            throw BadRequestException("Página inválida")
        }

        val blocks = blockchainController.getBlocks(id, pageNumber)

        val pageBuilder = BlockchainBlocksPageBuilder()

        pageBuilder.setupMenu(call.getUserRole())
        pageBuilder.setSetNodeInfo(id)
        pageBuilder.setResultSet(
            blocks
        )

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    suspend fun getBlockPage(call: ApplicationCall) {
        val nodeId = try {
            UUID.fromString(call.parameters["nodeId"])
        } catch (ex: Exception) {
            throw io.ktor.features.BadRequestException("Id inválida.")
        }

        val blockId = try {
            UUID.fromString(call.parameters["blockId"])
        } catch (ex: Exception) {
            throw io.ktor.features.BadRequestException("Id inválida.")
        }

        val block = blockchainController.getBlock(nodeId, blockId)

        val pageBuilder = BlockchainBlockPageBuilder()

        pageBuilder.setupMenu(call.getUserRole())
        pageBuilder.setNodeId(nodeId)
        pageBuilder.setBlock(block)

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    suspend fun getCreateNodePage(call: ApplicationCall) {
        val notaryId = try {
            UUID.fromString(call.parameters["notaryId"])
        } catch (ex: Exception) {
            throw io.ktor.features.BadRequestException("Id inválida.")
        }

        val pageBuilder = CreateNodePageBuilder()

        pageBuilder.setupMenu(call.getUserRole())
        pageBuilder.setNotaryId(notaryId)

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    suspend fun createNodePage(call: ApplicationCall) {
        val notaryId = try {
            UUID.fromString(call.parameters["notaryId"])
        } catch (ex: Exception) {
            throw io.ktor.features.BadRequestException("Id inválida.")
        }

        val form = call.receiveParameters()

        val rb = AddNodeRequest(
            notaryId, form["address"]!!
        )

        val node = blockchainController.createNode(rb)

        call.respondRedirect("/blockchain/nodes/${node.nodeId}")
    }

    suspend fun getNodePage(call: ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["nodeId"])
        } catch (ex: Exception) {
            throw io.ktor.features.BadRequestException("Id inválida.")
        }

        val node = blockchainController.getNode(id)

        val pageBuilder = BlockchainNodePageBuilder()
        pageBuilder.setupMenu(call.getUserRole())
        pageBuilder.setNode(node)

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    suspend fun getDocumentTransactions(call: ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw io.ktor.features.BadRequestException("Id inválida.")
        }

        val transactions = blockchainController.getDocumentTransactions(id)

        val pageBuilder = BlockchainDocumentTransactionsPageBuilder()
        pageBuilder.setupMenu(call.getUserRole())
        pageBuilder.setDocumentId(id)
        pageBuilder.setTransactions(transactions)

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    suspend fun checkDocumentValidity(call: ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw io.ktor.features.BadRequestException("Id inválida.")
        }

        val transaction = blockchainController.getDocumentLastTransaction(id)
        val document = documentController.getDocument(id)

        val pageBuilder = BlockchainDocumentValidationPageBuilder()
        pageBuilder.setupMenu(call.getUserRole())
        pageBuilder.setValid(transaction.documentHash == document.hash)
        pageBuilder.setTransaction(transaction)

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }
}