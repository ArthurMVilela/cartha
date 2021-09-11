package ui.handlers

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.response.*
import ui.features.getUserRole
import ui.pages.MainPageBuilder

/**
 * Handler para a página principal
 */
class MainPageHandler {
    /**
     * Recebe uma chamada da aplicação e retorna a tela principal
     *
     * @param call          chamada de aplicação
     */
    suspend fun mainPage(call: ApplicationCall) {
        val pageBuilder = MainPageBuilder()

        pageBuilder.setupMenu(call.getUserRole())

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }
}