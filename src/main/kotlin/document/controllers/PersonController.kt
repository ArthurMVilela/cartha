package document.controllers

import document.persistence.dao.person.OfficialDAO
import document.persistence.dao.person.PhysicalPersonDAO
import document.persistence.tables.person.OfficialTable
import document.person.*
import org.jetbrains.exposed.sql.Op
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

    fun getOfficials(notaryId: UUID): List<Official> {
        return officialDAO.selectMany(Op.build { OfficialTable.notaryId eq notaryId })
    }
}