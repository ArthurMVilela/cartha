package ui.handlers

import blockchain.NodeInfo
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.sessions.*
import newPersistence.ResultSet
import ui.controllers.AuthenticationController
import ui.features.UserSessionCookie
import ui.pages.BlockchainNodesPageBuilder
import ui.pages.BlockchainPageBuilder
import ui.pages.PageBuilder
import java.util.*

class BlockchainHandlers {
    private val authController = AuthenticationController()

    suspend fun getBlockchainPage(call: ApplicationCall) {
        val pageBuilder = BlockchainPageBuilder()
        val role = try {
            authController.getUserRole(call.sessions.get<UserSessionCookie>()!!)
        } catch (ex: Exception) {
            null
        }
        pageBuilder.setupMenu(role)

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    suspend fun getNodesPage(call: ApplicationCall) {
        val pageBuilder = BlockchainNodesPageBuilder()
        val role = try {
            authController.getUserRole(call.sessions.get<UserSessionCookie>()!!)
        } catch (ex: Exception) {
            null
        }
        pageBuilder.setupMenu(role)
        pageBuilder.setResultSet(ResultSet(listOf(
            NodeInfo(UUID.randomUUID(), "http://test.com", null)
        ),1,1,20))

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }
}