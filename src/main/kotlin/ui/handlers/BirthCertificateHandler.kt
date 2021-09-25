package ui.handlers

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.response.*
import ui.features.getUserRole
import ui.pages.document.civilRegistry.CreateBirthCertificatePageBuilder
import java.util.*

class BirthCertificateHandler {
    suspend fun getCreateBirthCertificatePage(call: ApplicationCall) {
        val pageBuilder = CreateBirthCertificatePageBuilder()

        pageBuilder.setupMenu(call.getUserRole())
        pageBuilder.setNotaryId(UUID.randomUUID())
        pageBuilder.setOfficialId(UUID.randomUUID())

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }
}