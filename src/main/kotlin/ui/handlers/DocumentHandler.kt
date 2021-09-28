package ui.handlers

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.response.*
import ui.controllers.DocumentController
import ui.features.getUserCpf
import ui.features.getUserRole
import ui.pages.document.CivilRegistryPageBuilder
import ui.pages.document.DocumentsPageBuilder

class DocumentHandler {
    private val documentController = DocumentController()

    suspend fun getCivilRegistryPage(call: ApplicationCall) {
        val pageBuilder = CivilRegistryPageBuilder()

        pageBuilder.setupMenu(call.getUserRole())

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    suspend fun getDocumentPage(call: ApplicationCall) {
        val cpf = call.getUserCpf()
        val bc = try {
            documentController.getBirthCertificatesByCpf(cpf)
        } catch (ex: Exception) {
            null
        }

        val affiliationBcs = documentController.getBirthCertificatesByAffiliation(cpf)

        val pageBuilder = DocumentsPageBuilder()

        pageBuilder.setupMenu(call.getUserRole())
        pageBuilder.setClientBirthCertificate(bc)
        pageBuilder.setAffiliationsBirthCertificate(affiliationBcs)

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }
}