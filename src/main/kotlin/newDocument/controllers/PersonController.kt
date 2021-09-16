package newDocument.controllers

import newDocument.person.*
import java.time.LocalDate
import java.util.*

class PersonController {
    fun createPhysicalPerson(p: PhysicalPerson):PhysicalPerson {
        return p
    }

    fun getPhysicalPerson(id: UUID): PhysicalPerson {
        return PhysicalPerson(
            id,
            null,
            "Fulano",
            "11122233344",
            LocalDate.now(),
            Sex.Male,
            Color.Black,
            CivilStatus.Single,
            "Brasileiro"
        )
    }

    fun getPhysicalPerson(cpf: String): PhysicalPerson {
        return PhysicalPerson(
            Person.createId(),
            null,
            "Fulano",
            cpf,
            LocalDate.now(),
            Sex.Male,
            Color.Black,
            CivilStatus.Single,
            "Brasileiro"
        )
    }
}