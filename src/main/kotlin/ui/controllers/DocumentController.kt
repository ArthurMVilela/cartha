package ui.controllers

import document.Notary
import document.handlers.notary.CreateNotaryRequest
import document.person.Official
import persistence.ResultSet
import java.util.*

class DocumentController {
    private val documentUrl = System.getenv("DOCUMENT_URL")?:throw Exception()
    private val documentClient = ui.controllers.DocumentClient(documentUrl)

    suspend fun getNotaries(page:Int):ResultSet<Notary> {
        return documentClient.getNotaries(page)
    }

    suspend fun createNotary(rb: CreateNotaryRequest): Notary {
        return documentClient.createNotary(rb)
    }

    suspend fun getNotary(id: UUID): Notary {
        return documentClient.getNotary(id)
    }

    suspend fun getOfficials(notaryId: UUID): List<Official> {
        return documentClient.getOfficials(notaryId)
    }
}