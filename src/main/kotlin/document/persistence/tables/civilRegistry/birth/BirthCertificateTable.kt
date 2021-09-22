package document.persistence.tables.civilRegistry.birth

import document.persistence.tables.person.PhysicalPersonTable
import document.persistence.tables.address.MunicipalityTable
import document.persistence.tables.civilRegistry.CivilRegistryDocumentTable
import document.person.Sex
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.`java-time`.date
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.util.*

object BirthCertificateTable:IdTable<UUID>("birth_certificate") {
    override val id: Column<EntityID<UUID>> = reference("id", CivilRegistryDocumentTable.id)
    val personId = optReference("person_id", PhysicalPersonTable.id)
    val name = varchar("name", 120)
    val sex = enumeration("sex", Sex::class)
    val municipalityOfBirthId = reference("municipality_of_birth_id", MunicipalityTable.id)
    val municipalityOfRegistryId = reference("municipality_of_registry_id", MunicipalityTable.id)
    val placeOfBirth = varchar("place_of_birth", 120)
    val dateTimeOfBirth = datetime("date_time_if_birth")
    val dateOfRegistry = date("date_of_registry")
    val dnNumber = char("dn_number", 11)
}