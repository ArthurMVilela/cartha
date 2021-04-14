package document

import document.exceptions.RecordNotFoundException
import document.persistency.dao.PhysicalPersonDAO
import org.jetbrains.exposed.exceptions.ExposedSQLException
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class PersonController {
    val physicalPersonDAO = PhysicalPersonDAO()

    /**
     * Cria nos registros uma pessoa física (PhysicalPerson)
     *
     * @param person        Pessoa física a ser registrada
     *
     * @return Pessoa física cadastrada
     */
    fun createPhysicalPerson(person: PhysicalPerson):PhysicalPerson {
        person.id = createPersonId()

        try {
            physicalPersonDAO.insert(person)
        } catch (ex:ExposedSQLException) {
            throw ex
        }

        val registered: Person?

        try {
            registered = physicalPersonDAO.select(person.id!!)
        } catch (ex:ExposedSQLException) {
            throw ex
        }

        if (registered != null) {
            return person
        }

        throw RecordNotFoundException("Não é possivel pegar registro recém criado.")
    }

    private fun createPersonId():String {
        val md = MessageDigest.getInstance("SHA")
        val now = LocalDateTime.now(ZoneOffset.UTC)
        val content = now.toString().toByteArray()

        return Base64.getUrlEncoder().encodeToString(md.digest(content))
    }
}