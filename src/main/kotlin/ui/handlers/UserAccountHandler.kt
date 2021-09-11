package ui.handlers

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.sessions.*
import io.ktor.utils.io.*
import ui.controllers.AuthenticationController
import ui.features.UserSessionCookie
import ui.features.getUserRole
import ui.pages.LoginPageBuilder
import ui.util.Util

class UserAccountHandler {
    private val authController = AuthenticationController()
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
}