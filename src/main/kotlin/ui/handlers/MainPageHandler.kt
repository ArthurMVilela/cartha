package ui.handlers

import authentication.Role
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.response.*
import ui.util.Util

class MainPageHandler {
    /**
     * Recebe uma chamada da aplicação e retorna a tela principal
     *
     * @param call          chamada de aplicação
     */
    suspend fun mainPage(call: ApplicationCall) {
        val data = mutableMapOf<String, Any?>()
        Util.addMenuToLayoutMap(data, null)
        call.respond(HttpStatusCode.OK, FreeMarkerContent("main.ftl", data))
    }
}