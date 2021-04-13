package document.persistency.tables

import document.CivilStatus
import document.Color
import document.Sex
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime

object deathCertificateTable : Table("death_certificate") {
    val id = char("id", 44).references(civilRegistryDocumentTable.id)
    val sex = enumeration("sex", Sex::class)
    val color = enumeration("color", Color::class)
    val civilStatus = enumeration("civil_status", CivilStatus::class)
    val age = integer("age")

    val birthPlace = varchar("birth_place", 120)
    val documentOfIdentity = varchar("document_of_identity", 20)
    val affiliationId = char("id", 32).references(affiliationTable.id)
    val residency = varchar("residency", 120)

    val dateTimeOfDeath = datetime("datetime_of_death")
    val placeOfDeath = varchar("place_of_death", 80)
    val causeOfDeath  = varchar("cause_of_death", 80)
    val burialOrCremationLocation = varchar("burial_or_cremation_location", 120)
    val documentDeclaringDeath = varchar("document_declaring_death", 80)

    override val primaryKey = PrimaryKey(id, name = "pk_death_certificate_id")
}