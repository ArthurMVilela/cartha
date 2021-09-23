package ui.handlers

import document.handlers.notary.CreateNotaryRequest
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import org.jetbrains.exposed.sql.not
import serviceExceptions.BadRequestException
import ui.controllers.DocumentController
import ui.features.getUserRole
import ui.pages.document.CreateNotaryPageBuilder
import ui.pages.document.NotariesPageBuilder
import ui.pages.document.NotaryPageBuilder
import java.util.*

class NotaryHandler {
    private val documentController = DocumentController()

    suspend fun getNotariesPage(call: ApplicationCall) {
        val pageNumber = try {
            call.request.queryParameters["page"]?.toInt()?:1
        } catch (ex: Exception) {
            throw BadRequestException("Página inválida")
        }

        if (pageNumber < 1) {
            throw BadRequestException("Página inválida")
        }

        val notaries = try {
            documentController.getNotaries(pageNumber)
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw NotFoundException("Página não encontrada.")
        }

        val pageBuilder = NotariesPageBuilder()

        pageBuilder.setupMenu(call.getUserRole())
        pageBuilder.setResultSet(notaries)

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    suspend fun getCreateNotaryPage(call: ApplicationCall) {
        val pageBuilder = CreateNotaryPageBuilder()

        pageBuilder.setupMenu(call.getUserRole())

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    suspend fun createNotary(call: ApplicationCall) {
        val notary = documentController.createNotary(parseCreateNotaryForm(call.receiveParameters()))

        call.respondRedirect("/notary/${notary.id}")
    }

    suspend fun getNotaryPage(call: ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw io.ktor.features.BadRequestException("Id inválida.")
        }

        val notary = try {
            documentController.getNotary(id)
        } catch (ex: Exception) {
            throw ex
        }

        val pageBuilder = NotaryPageBuilder()

        pageBuilder.setupMenu(call.getUserRole())
        pageBuilder.setUpNotary(notary)

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    private fun parseCreateNotaryForm(form: Parameters): CreateNotaryRequest {
        return CreateNotaryRequest(
            form["name"]?:throw BadRequestException("Nome inválido ou nulo."),
            form["cnpj"]?:throw BadRequestException("CNPJ inválido ou nulo."),
            form["cns"]?:throw BadRequestException("CNS inválido ou nulo.")
        )
    }
}