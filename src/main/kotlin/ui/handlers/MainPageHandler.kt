package ui.handlers

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.response.*

class MainPageHandler {
    /**
     * Recebe uma chamada da aplicação e retorna a tela principal
     *
     * @param call          chamada de aplicação
     */
    suspend fun mainPage(call: ApplicationCall) {
        call.respond(HttpStatusCode.OK, FreeMarkerContent("main.ftl", null))
    }
}