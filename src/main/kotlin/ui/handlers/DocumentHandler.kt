package ui.handlers

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.response.*
import ui.features.getUserRole
import ui.pages.document.CivilRegistryPageBuilder

class DocumentHandler {
    suspend fun getCivilRegistryPage(call: ApplicationCall) {
        val pageBuilder = CivilRegistryPageBuilder()

        pageBuilder.setupMenu(call.getUserRole())

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    suspend fun getDocumentPage(call: ApplicationCall) {

    }
}