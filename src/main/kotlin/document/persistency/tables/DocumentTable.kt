package document.persistency.tables

import document.*
import document.civilRegistry.Affiliation
import document.civilRegistry.MatrimonialRegime
import document.civilRegistry.Spouse
import document.persistency.tables.BirthCertificateTable.references
import document.persistency.tables.MarriageCertificateTable.references
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.date
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

object DocumentTable : Table("document") {
    val id = char("id", 44)
    val status = enumeration("status", DocumentStatus::class)
    val officialId = char("official_id", 32)
    val notaryId = char("notaryId", 32)

    override val primaryKey = PrimaryKey(id, name = "pk_document_id")
}

object CivilRegistryDocumentTable : Table("civil_registry_document") {
    val id = char("id", 44).references(DocumentTable.id)
    val registration = char("registration", 32)

    override val primaryKey = PrimaryKey(id, name = "pk_civil_registry_document_id")
}

object ObservationsAndRegisteringTable : Table("observations_and_registering") {
    val id = char("id", 44).references(CivilRegistryDocumentTable.id)
    val text = text("text")

    override val primaryKey = PrimaryKey(id, name = "pk_observations_and_registering_id")
}

object BirthCertificateTable : Table("birth_certificate") {
    val id = char("id", 44).references(CivilRegistryDocumentTable.id)
    val dateTimeOfBirth = datetime("datetime_of_birth")
    val municipalityOfBirth = varchar("municipality_of_birth", 80)
    val UFOfBirth = enumeration("uf_of_birth", UF::class)
    val municipalityOfRegistry = varchar("municipality_of_registry", 80)
    val UFOfRegistry = enumeration("uf_of_registry", UF::class)
    val twin = bool("twin")
    val dateOfRegistry = date("date_of_registry")
    val DNNumber = char("dn_number", 6)

    val personId = char("person_id", 44).references(PhysicalPersonTable.id)
    val cpf = char("cpf", 9)
    val name = varchar("name", 120)
    val dateOfBirth = date("birthday")
    val sex = enumeration("sex", Sex::class)

    override val primaryKey = PrimaryKey(id, name = "pk_birth_certificate_id")
}

object MarriageCertificateTable : Table("marriage_certificate") {
    val id = char("id", 44).references(CivilRegistryDocumentTable.id)
    val firstSpouseId = char("first_spouse_id", 32)
    val secondSpouseId = char("first_spouse_id", 32)
    val dateOfRegistry = date("date_of_registry")
    val matrimonialRegime = enumeration("matrimonial_regime", MatrimonialRegime::class)

    override val primaryKey = PrimaryKey(id, name = "pk_marriage_certificate_id")
}

object DeathCertificateTable : Table("death_certificate") {
    val id = char("id", 44).references(CivilRegistryDocumentTable.id)
    val sex = enumeration("sex", Sex::class)
    val color = enumeration("color", Color::class)
    val civilStatus = enumeration("civil_status", CivilStatus::class)
    val age = integer("age")

    val birthPlace = varchar("birth_place", 120)
    val documentOfIdentity = varchar("document_of_identity", 20)
    val affiliationId = char("id", 32).references(AffiliationTable.id)
    val residency = varchar("residency", 120)

    val dateTimeOfDeath = datetime("datetime_of_death")
    val placeOfDeath = varchar("place_of_death", 80)
    val causeOfDeath  = varchar("cause_of_death", 80)
    val burialOrCremationLocation = varchar("burial_or_cremation_location", 120)
    val documentDeclaringDeath = varchar("document_declaring_death", 80)

    override val primaryKey = PrimaryKey(id, name = "pk_death_certificate_id")
}

object SpouseTable : Table("spouse") {
    val id = char("first_spouse_id", 32)
    val singleName = varchar("name", 120)
    val marriedName = varchar("name", 120)
    val personId = char("id",32)
    val birthday = date("birthday")
    val nationality = varchar("nationality", 30)
}

object AffiliationTable : Table("affiliation") {
    val id = char("id", 32)
    val personId = char("person_id",32)
    val name = varchar("name", 120)
    val UF = enumeration("uf", UF::class).nullable()
    val Municipality = varchar("municipality", 80).nullable()

    override val primaryKey = PrimaryKey(id, name = "pk_affiliation_id")
}

object SpouseAffiliationTable : Table("spouse_affiliation") {
    val id = integer("id").autoIncrement()
    val spouseId = char("spouse_id", 44).references(SpouseTable.id)
    val affiliationId = char("affiliation_id", 32).references(AffiliationTable.id)

    override val primaryKey = PrimaryKey(id, name = "pk_spouse_affiliation_id")
}

object BirthCertificateAffiliationTable : Table("birth_certificate_affiliation") {
    val id = integer("id").autoIncrement()
    val birthCertificateId = char("birth_certificate_id", 44).references(BirthCertificateTable.id)
    val affiliationId = char("affiliation_id", 32).references(AffiliationTable.id)

    override val primaryKey = PrimaryKey(id, name = "pk_birth_certificate_affiliation_id")
}

object GrandparentTable : Table("grandparent") {
    val id = char("id", 32)
    val personId = char("person_id",32)
    val name = varchar("name", 120)
    val UF = enumeration("uf", UF::class).nullable()
    val Municipality = varchar("municipality", 80).nullable()

    override val primaryKey = PrimaryKey(id, name = "pk_grandparent_id")
}

object BirthCertificateGrandparentTable : Table("birth_certificate_grandparent") {
    val id = integer("id").autoIncrement()
    val birthCertificateId = char("birth_certificate_id", 44).references(BirthCertificateTable.id)
    val grandparentId = char("grandparent_id", 32).references(GrandparentTable.id)

    override val primaryKey = PrimaryKey(id, name = "pk_birth_certificate_grandparent_id")
}

object TwinTable : Table("twin") {
    val id = char("id", 32)
    val birthCertificateId = char("id", 44).references(BirthCertificateTable.id)
    val registration = char("registration", 32)
    val name = varchar("name", 120)

    override val primaryKey = PrimaryKey(id, name = "pk_twin_id")
}

object BirthCertificateTwinTable : Table("birth_certificate_twin") {
    val id = integer("id").autoIncrement()
    val birthCertificateId = char("birth_certificate_id", 44).references(BirthCertificateTable.id)
    val twinId = char("twin_id", 32).references(TwinTable.id)

    override val primaryKey = PrimaryKey(id, name = "pk_birth_certificate_twin_id")
}