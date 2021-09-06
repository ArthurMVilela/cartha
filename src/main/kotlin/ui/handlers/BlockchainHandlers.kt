package ui.handlers

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.sessions.*
import newPersistence.ResultSet
import ui.controllers.AuthenticationController
import ui.features.UserSessionCookie
import ui.pages.BlockchainNodesPageBuilder

class BlockchainHandlers {
    private val authController = AuthenticationController()

    suspend fun getNodesPage(call: ApplicationCall) {
        val pageBuilder = BlockchainNodesPageBuilder()
        val role = try {
            authController.getUserRole(call.sessions.get<UserSessionCookie>()!!)
        } catch (ex: Exception) {
            null
        }
        pageBuilder.setupMenu(role)
        pageBuilder.setResultSet(ResultSet(listOf(),1,1,20))

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }
}