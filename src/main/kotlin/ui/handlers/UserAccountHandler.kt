package ui.handlers

import authentication.UserSession
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.utils.io.*
import ui.controllers.AuthenticationClient

class UserAccountHandler {
    private val authenticationClient = AuthenticationClient()
    /**
     * Recebe uma chamada da aplicação e retorna a tela de login
     *
     * @param call          chamada de aplicação
     */
    suspend fun loginPage(call: ApplicationCall) {
        call.respond(HttpStatusCode.OK, FreeMarkerContent("login.ftl", null))
    }

    suspend fun postLogin(call: ApplicationCall) {
        val form = call.receiveParameters()

        try {
            val email = if(form["email"].isNullOrEmpty()) null else form["email"]
            val cpf = if(form["cpf"].isNullOrEmpty()) null else form["cpf"]
            val cnpj = if(form["cnpj"].isNullOrEmpty()) null else form["cnpj"]
            val userSession = authenticationClient.login(email, cpf, cnpj, form["password"]!!)
            call.respond(HttpStatusCode.OK, userSession.id.toString())
        } catch (ex: Exception) {
            ex.printStack()
            call.respond(FreeMarkerContent("login.ftl", mapOf("errorMessage" to ex.message)))
        }
    }

    fun userAccountPage(call: ApplicationCall) {
        TODO()
    }
}