package ui.handlers

import authentication.Role
import document.handlers.person.CreateOfficialRequest
import document.person.Sex
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.sessions.*
import io.ktor.utils.io.*
import ui.controllers.AuthenticationController
import ui.controllers.DocumentController
import ui.features.UserSessionCookie
import ui.features.getUserRole
import ui.pages.CreateOfficialPageBuilder
import ui.pages.LoginPageBuilder
import ui.util.Util
import java.util.*

class UserAccountHandler {
    private val authController = AuthenticationController()
    private val documentController = DocumentController()

    /**
     * Recebe uma chamada da aplicação e retorna a tela de login
     *
     * @param call          chamada de aplicação
     */
    suspend fun loginPage(call: ApplicationCall) {
        val pageBuilder = LoginPageBuilder()
        pageBuilder.setupMenu(call.getUserRole())

        val sessionCookie = call.sessions.get<UserSessionCookie>()
        if (sessionCookie != null) {
            if (authController.isSessionValid(sessionCookie)) {
                call.respondRedirect("/")
                return
            }
        }

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    /**
     * Recebe uma chamada da aplicação e processa o formulário de login
     *
     * @param call          chamada de aplicação
     */
    suspend fun postLogin(call: ApplicationCall) {
        val form = call.receiveParameters()

        val sessionCookie = call.sessions.get<UserSessionCookie>()
        if (sessionCookie != null) {
            if (authController.isSessionValid(sessionCookie)) {
                call.respondRedirect("/")
                return
            }
        }

        try {
            val email = if(form["email"].isNullOrEmpty()) null else form["email"]
            val cpf = if(form["cpf"].isNullOrEmpty()) null else form["cpf"]
            val cnpj = if(form["cnpj"].isNullOrEmpty()) null else form["cnpj"]
            val userSession = authController.login(email, cpf, cnpj, form["password"]!!)
            call.sessions.set(UserSessionCookie(userSession.id.toString()))
            call.respondRedirect("/")
        } catch (ex: Exception) {
            val pageBuilder = LoginPageBuilder()
            pageBuilder.setupMenu(call.getUserRole())
            pageBuilder.setErrorMessage(ex.message?:"Erro inesperado")

            val page = pageBuilder.build()
            call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
        }
    }

    /**
     * Recebe uma chamada da aplicação e o logout
     *
     * @param call          chamada de aplicação
     */
    suspend fun logout(call: ApplicationCall) {
        val sessionCookie = call.sessions.get<UserSessionCookie>()
        if (sessionCookie != null) {
            if (authController.isSessionValid(sessionCookie)) {
                authController.logout(sessionCookie)
                call.sessions.clear<UserSessionCookie>()
                call.respondRedirect("/")
                return
            }
        }

        call.respondRedirect("/")
    }

    fun userAccountPage(call: ApplicationCall) {
        TODO()
    }

    suspend fun getCreateOfficialPage(call: ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw io.ktor.features.BadRequestException("Id inválida.")
        }

        val pageBuilder = CreateOfficialPageBuilder()

        pageBuilder.setupMenu(call.getUserRole())
        pageBuilder.setNotaryId(id)

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    suspend fun createOfficial(call: ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw io.ktor.features.BadRequestException("Id inválida.")
        }

        val form = call.receiveParameters()

        val user = try {
            authController.createAccount(
                form["name"]!!,
                Role.Official,
                form["email"]!!,
                form["cpf"]!!,
                null,
                form["password"]!!,
                id
            )
        } catch (ex: Exception) {
            throw ex
        }

        val official = try {
            documentController.createOfficial(CreateOfficialRequest(
                user.id,
                user.name,
                user.cpf!!,
                Sex.valueOf(form["sex"]!!),
                id
            ))
        } catch (ex: Exception) {
            throw ex
        }

        call.respondRedirect("/notary/$id")
    }
}