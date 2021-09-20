package newDocument.controllers

import newDocument.Notary
import newDocument.persistence.dao.NotaryDAO
import newPersistence.ResultSet
import java.util.*

class NotaryController {
    private val notaryDAO = NotaryDAO()

    fun createNotary(notary: Notary): Notary {
        return notaryDAO.insert(notary)
    }
    fun getNotary(id: UUID): Notary? {
        return notaryDAO.select(id)
    }
    fun getNotary(cnpj: String): Notary? {
        return notaryDAO.select(cnpj)
    }
    fun getNotaries(page:Int):ResultSet<Notary> {
        return notaryDAO.selectAll(page)
    }
}