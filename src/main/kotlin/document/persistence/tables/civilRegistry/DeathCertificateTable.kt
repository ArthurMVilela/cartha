package document.persistence.tables.civilRegistry

import document.CivilStatus
import document.Color
import document.Sex
import document.persistence.tables.PhysicalPersonTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.`java-time`.datetime

object DeathCertificateTable:IdTable<String>("death_certificate") {
    override val id: Column<EntityID<String>> = char("id", 44).entityId().references(CivilRegistryDocumentTable.id)

    val personId = reference("person_id", PhysicalPersonTable.id)
    val sex = enumeration("sex", Sex::class)
    val color = enumeration("color", Color::class)
    val civilStatus = enumeration("civil_status", CivilStatus::class)
    val age = integer("age")

    val birthPlace = varchar("birthplace", 120)
    val documentOfIdentity = varchar("document_of_identity", 80)
    val residency = varchar("residency", 120)

    val dateTimeOfDeath = datetime("datetime_of_death")
    val placeOfDeath = varchar("place_of_death", 120)
    val causeOfDeath = varchar("cause_of_death", 80)
    val burialOrCremationLocation = varchar("burial_or_cremation_location", 120).nullable()
    val documentDeclaringDeath = varchar("document_declaring_death", 80)

    override val primaryKey: PrimaryKey? = PrimaryKey(id, name = "pk_death_certificate_id")
}