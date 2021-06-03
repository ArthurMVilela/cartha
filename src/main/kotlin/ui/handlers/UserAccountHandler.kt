package ui.handlers

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.response.*

class UserAccountHandler {
    /**
     * Recebe uma chamada da aplicação e retorna a tela de login
     *
     * @param call          chamada de aplicação
     */
    suspend fun loginPage(call: ApplicationCall) {
        call.respond(HttpStatusCode.OK, FreeMarkerContent("login.ftl", null))
    }

    fun postLogin(call: ApplicationCall) {
        TODO()
    }

    fun userAccountPage(call: ApplicationCall) {
        TODO()
    }
}