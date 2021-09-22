package document.persistence.tables.civilRegistry.death

import document.persistence.tables.person.PhysicalPersonTable
import document.civilRegistry.IdentityDocumentType
import document.persistence.tables.address.AddressTable
import document.persistence.tables.address.MunicipalityTable
import document.persistence.tables.civilRegistry.CivilRegistryDocumentTable
import document.person.CivilStatus
import document.person.Sex
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.util.*

object DeathCertificate:IdTable<UUID>("death_certificate") {
    override val id: Column<EntityID<UUID>> = reference("id", CivilRegistryDocumentTable.id)

    val personId = optReference("person_id", PhysicalPersonTable.id)
    val name = varchar("name", 140)
    val sex = enumeration("sex", Sex::class)
    val color = enumeration("color", Sex::class)
    val civilStatus = enumeration("civil_status", CivilStatus::class)
    val age = integer("age")
    val placeOfBirthId = reference("place_of_birth_id", MunicipalityTable.id)
    val identityDocument = varchar("identity_document",14)
    val identityDocumentType = enumeration("identity_document_type", IdentityDocumentType::class)
    val voterId = char("voter_id", 14).nullable()
    val residenceId = reference("residence_id", AddressTable.id)
    val dateTimeOfDeath = datetime("date_time_of_birth")
    val placeOfDeath = varchar("place_of_death", 140)
    val causeOfDeath = varchar("cause_of_death", 140)
    val placeOfBurialOrCremationId = optReference("place_of_burial_or_cremation_id", AddressTable.id)
    val medicalDocumentDeclaringDeath = varchar("medical_document_declaring_death", 120).nullable()
}