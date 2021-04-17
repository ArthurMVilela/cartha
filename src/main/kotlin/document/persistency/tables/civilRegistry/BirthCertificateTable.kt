package document.persistency.tables.civilRegistry

import document.Sex
import document.UF
import document.persistency.tables.PhysicalPersonTable
import document.persistency.tables.civilRegistry.AffiliationTable.nullable
import document.persistency.tables.civilRegistry.DeathCertificateTable.entityId
import document.persistency.tables.civilRegistry.DeathCertificateTable.references
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.`java-time`.date
import org.jetbrains.exposed.sql.`java-time`.datetime

object BirthCertificateTable:IdTable<String>("birth_certificate") {
    override val id: Column<EntityID<String>> = DeathCertificateTable.char("id", 44).entityId().references(CivilRegistryDocumentTable.id)
    val dateTimeOfBirth = datetime("datetime_of_birth")
    val municipalityOfBirth = varchar("municipality_of_birth", 80).nullable()
    val UFOfBirth = enumeration("uf_of_birth", UF::class)
    val municipalityOfRegistry = varchar("municipality_of_registry", 80).nullable()
    val UFOfRegistry = enumeration("uf_of_registry", UF::class)
    val twin = bool("twin")
    val dateOfRegistry = date("date_of_registry")
    val DNNumber = char("dn_number", 6)
    val personId = reference("person_id", PhysicalPersonTable.id)
    val cpf = char("cpf", 11)
    val name = varchar("name", 120)
    val sex = enumeration("sex", Sex::class)

    override val primaryKey: PrimaryKey? = PrimaryKey(id, name = "pk_birth_certificate_id")
}