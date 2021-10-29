package document.controllers

import document.Document
import document.persistence.dao.DocumentDAO
import java.util.*

class DocumentController {
    private val documentDAO = DocumentDAO()

    fun getDocumentById(id: UUID): Document? {
        return documentDAO.select(id)
    }
}