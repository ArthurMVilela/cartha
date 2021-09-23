package ui.controllers

import document.Notary
import persistence.ResultSet

class DocumentController {
    private val nodeManagerURL = System.getenv("DOCUMENT_URL")?:throw Exception()
    private val documentClient = DocumentClient(nodeManagerURL)

    suspend fun getNotaries(page:Int):ResultSet<Notary> {
        return documentClient.getNotaries(page)
    }
}