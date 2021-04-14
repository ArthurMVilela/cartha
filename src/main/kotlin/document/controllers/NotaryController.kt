package document.controllers

import document.Notary
import document.exceptions.RecordNotFoundException
import document.persistency.dao.NotaryDAO
import org.jetbrains.exposed.exceptions.ExposedSQLException
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

/**
 * Classe Controller para as funcionalidades de cartório (Notary)
 */
class NotaryController {
    val notaryDAO = NotaryDAO()

    /**
     * Cria nos registros de um cartório (Notary)
     *
     * @param notary        Cartório a ser registrada
     *
     * @return Cartório cadastrado
     */
    fun createNotary(notary: Notary): Notary {
        notary.id = createNotaryId()

        try {
            notaryDAO.insert(notary)
        } catch (ex: ExposedSQLException) {
            throw ex
        }

        val registered: Notary?

        try {
            registered = notaryDAO.select(notary.id!!)
        } catch (ex: ExposedSQLException) {
            throw ex
        }

        if (registered != null) {
            return registered
        }

        throw RecordNotFoundException("Não é possivel pegar registro recém criado.")
    }

    /**
     * Pega nos registros de um cartório (Notary)
     * @param id        id do cartório
     * @return Cartório encontrado
     */
    fun getNotary(id:String): Notary? {
        val found: Notary?
        try {
            val found = notaryDAO.select(id)
            return found
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    /**
     * Atualiza nos registros um cartório (Notary)
     * @param id        id do cartório
     * @param new       dados novos
     */
    fun updateNotary(id: String, new: Notary) {
        try {
            notaryDAO.update(id, new)
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    /**
     * Deleta nos registros de um cartório (Notary)
     * @param id        id do cartório
     */
    fun deleteNotary(id: String) {
        try {
            notaryDAO.delete(id)
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    /**
     * Cria um id para o registro de um cartório
     * @return id
     */
    private fun createNotaryId():String {
        val md = MessageDigest.getInstance("SHA")
        val now = LocalDateTime.now(ZoneOffset.UTC)
        val content = now.toString().toByteArray()

        return Base64.getUrlEncoder().encodeToString(md.digest(content))
    }
}