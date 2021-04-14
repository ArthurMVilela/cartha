package document.controllers

import document.LegalPerson
import document.Official
import document.Person
import document.PhysicalPerson
import document.exceptions.RecordNotFoundException
import document.persistency.dao.LegalPersonDAO
import document.persistency.dao.OfficialDAO
import document.persistency.dao.PhysicalPersonDAO
import org.jetbrains.exposed.exceptions.ExposedSQLException
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

/**
 * Classe Controller para todas as classes de pessoa
 */
class PersonController {
    val physicalPersonDAO = PhysicalPersonDAO()
    val legalPersonDAO = LegalPersonDAO()
    val officialDAO = OfficialDAO()

    /**
     * Cria nos registros uma pessoa física (PhysicalPerson)
     *
     * @param person        Pessoa física a ser registrada
     *
     * @return Pessoa física cadastrada
     */
    fun createPhysicalPerson(person: PhysicalPerson): PhysicalPerson {
        person.id = createPersonId()

        try {
            physicalPersonDAO.insert(person)
        } catch (ex:ExposedSQLException) {
            throw ex
        }

        val registered: PhysicalPerson?

        try {
            registered = physicalPersonDAO.select(person.id!!)
        } catch (ex:ExposedSQLException) {
            throw ex
        }

        if (registered != null) {
            return registered
        }

        throw RecordNotFoundException("Não é possivel pegar registro recém criado.")
    }

    /**
     * Pega nos registros uma pessoa física (PhysicalPerson)
     * @param id        id da pessoa física
     * @return Pessoa física encontrada
     */
    fun getPhysicalPerson(id:String): PhysicalPerson? {
        val found:PhysicalPerson?
        try {
            val found = physicalPersonDAO.select(id)
            return found
        } catch (ex:ExposedSQLException) {
            throw ex
        }
    }

    /**
     * Atualiza nos registros uma pessoa física (PhysicalPerson)
     * @param id        id da pessoa física
     * @param new       dados novos
     */
    fun updatePhysicalPerson(id: String, new: PhysicalPerson) {
        try {
            physicalPersonDAO.update(id, new)
        } catch (ex:ExposedSQLException) {
            throw ex
        }
    }

    /**
     * Deleta nos registros uma pessoa física (PhysicalPerson)
     * @param id        id da pessoa física
     */
    fun deletePhysicalPerson(id: String) {
        try {
            physicalPersonDAO.delete(id)
        } catch (ex:ExposedSQLException) {
            throw ex
        }
    }

    /**
     * Cria nos registros uma pessoa juridica (PhysicalPerson)
     *
     * @param person        Pessoa juridica a ser registrada
     *
     * @return Pessoa física cadastrada
     */
    fun createLegalPerson(person: LegalPerson): LegalPerson {
        person.id = createPersonId()

        try {
            legalPersonDAO.insert(person)
        } catch (ex:ExposedSQLException) {
            throw ex
        }

        val registered: LegalPerson?

        try {
            registered = legalPersonDAO.select(person.id!!)
        } catch (ex:ExposedSQLException) {
            throw ex
        }

        if (registered != null) {
            return registered
        }

        throw RecordNotFoundException("Não é possivel pegar registro recém criado.")
    }

    /**
     * Pega nos registros uma pessoa física (PhysicalPerson)
     * @param id        id da pessoa física
     * @return Pessoa física encontrada
     */
    fun getLegalPerson(id:String): LegalPerson? {
        val found:PhysicalPerson?
        try {
            val found = legalPersonDAO.select(id)
            return found
        } catch (ex:ExposedSQLException) {
            throw ex
        }
    }

    /**
     * Atualiza nos registros uma pessoa física (PhysicalPerson)
     * @param id        id da pessoa física
     * @param new       dados novos
     */
    fun updateLegalPerson(id: String, new: LegalPerson) {
        try {
            legalPersonDAO.update(id, new)
        } catch (ex:ExposedSQLException) {
            throw ex
        }
    }

    /**
     * Deleta nos registros uma pessoa física (PhysicalPerson)
     * @param id        id da pessoa física
     */
    fun deleteLegalPerson(id: String) {
        try {
            legalPersonDAO.delete(id)
        } catch (ex:ExposedSQLException) {
            throw ex
        }
    }

    /**
     * Cria nos registros uma pessoa juridica (PhysicalPerson)
     *
     * @param person        Pessoa juridica a ser registrada
     *
     * @return Pessoa física cadastrada
     */
    fun createOfficial(person: Official): Official {
        person.id = createPersonId()

        try {
            officialDAO.insert(person)
        } catch (ex:ExposedSQLException) {
            throw ex
        }

        val registered: Official?

        try {
            registered = officialDAO.select(person.id!!)
        } catch (ex:ExposedSQLException) {
            throw ex
        }

        if (registered != null) {
            return registered
        }

        throw RecordNotFoundException("Não é possivel pegar registro recém criado.")
    }

    /**
     * Pega nos registros uma pessoa física (PhysicalPerson)
     * @param id        id da pessoa física
     * @return Pessoa física encontrada
     */
    fun getOfficial(id:String): Official? {
        val found:Official?
        try {
            val found = officialDAO.select(id)
            return found
        } catch (ex:ExposedSQLException) {
            throw ex
        }
    }

    /**
     * Atualiza nos registros uma pessoa física (PhysicalPerson)
     * @param id        id da pessoa física
     * @param new       dados novos
     */
    fun updateOfficial(id: String, new: Official) {
        try {
            officialDAO.update(id, new)
        } catch (ex:ExposedSQLException) {
            throw ex
        }
    }

    /**
     * Deleta nos registros uma pessoa física (PhysicalPerson)
     * @param id        id da pessoa física
     */
    fun deleteOfficial(id: String) {
        try {
            officialDAO.delete(id)
        } catch (ex:ExposedSQLException) {
            throw ex
        }
    }

    /**
     * Cria um id para o registro de uma pessoa
     * @return id
     */
    private fun createPersonId():String {
        val md = MessageDigest.getInstance("SHA")
        val now = LocalDateTime.now(ZoneOffset.UTC)
        val content = now.toString().toByteArray()

        return Base64.getUrlEncoder().encodeToString(md.digest(content))
    }
}