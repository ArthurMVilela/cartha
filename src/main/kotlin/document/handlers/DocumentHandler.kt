package document.handlers

import document.controllers.DocumentController
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import java.util.*

class DocumentHandler {
    private val documentController = DocumentController()

    suspend fun getDocumentById(call: ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw serviceExceptions.BadRequestException("id não válida")
        }

        val doc = documentController.getDocumentById(id)?:throw NotFoundException("Documento não encontrado")

        call.respond(HttpStatusCode.OK, doc)
    }
}