package newDocument.controllers

import newDocument.persistence.dao.person.OfficialDAO
import newDocument.persistence.dao.person.PhysicalPersonDAO
import newDocument.person.*
import java.util.*

class PersonController {
    private val physicalPersonDAO = PhysicalPersonDAO()
    private val officialDAO = OfficialDAO()

    fun createPhysicalPerson(p: PhysicalPerson):PhysicalPerson {
        return physicalPersonDAO.insert(p)
    }

    fun getPhysicalPerson(id: UUID): PhysicalPerson? {
        return physicalPersonDAO.select(id)
    }

    fun getPhysicalPerson(cpf: String): PhysicalPerson? {
        return physicalPersonDAO.select(cpf)
    }

    fun createOfficial(o: Official): Official {
        return officialDAO.insert(o)
    }

    fun getOfficial(id: UUID): Official? {
        return officialDAO.select(id)
    }

    fun getOfficial(cpf: String): Official? {
        return officialDAO.select(cpf)
    }
}