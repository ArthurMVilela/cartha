package ui.handlers

import blockchain.Block
import blockchain.BlockInfo
import blockchain.NodeInfo
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.response.*
import persistence.ResultSet
import serviceExceptions.BadRequestException
import ui.controllers.AuthenticationController
import ui.controllers.BlockchainController
import ui.features.getUserRole
import ui.pages.blockchain.BlockchainBlockPageBuilder
import ui.pages.blockchain.BlockchainBlocksPageBuilder
import ui.pages.blockchain.BlockchainNodesPageBuilder
import ui.pages.blockchain.BlockchainPageBuilder
import java.time.LocalDateTime
import java.util.*

class BlockchainHandlers {
    private val authController = AuthenticationController()
    private val blockchainController = BlockchainController()

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
        val pageBuilder = BlockchainBlocksPageBuilder()

        pageBuilder.setupMenu(call.getUserRole())
        pageBuilder.setSetNodeInfo(NodeInfo(UUID.randomUUID(), "", LocalDateTime.now()))
        pageBuilder.setResultSet(
            ResultSet(
                listOf(
                    BlockInfo(UUID.randomUUID(), LocalDateTime.now(), "BLABLA", UUID.randomUUID()
                    )),
                1, 1, 20
            )
        )

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    suspend fun getBlockPage(call: ApplicationCall) {
        val pageBuilder = BlockchainBlockPageBuilder()

        pageBuilder.setupMenu(call.getUserRole())
        pageBuilder.setBlock(Block(LocalDateTime.now(), listOf(), "BleBle", UUID.randomUUID()))

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }
}