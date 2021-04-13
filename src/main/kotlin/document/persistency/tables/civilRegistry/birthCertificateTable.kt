package document.persistency.tables.civilRegistry

import document.Sex
import document.UF
import document.persistency.tables.physicalPersonTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.date
import org.jetbrains.exposed.sql.`java-time`.datetime

object birthCertificateTable : Table("birth_certificate") {
    val id = char("id", 44).references(civilRegistryDocumentTable.id)
    val dateTimeOfBirth = datetime("datetime_of_birth")
    val municipalityOfBirth = varchar("municipality_of_birth", 80)
    val UFOfBirth = enumeration("uf_of_birth", UF::class)
    val municipalityOfRegistry = varchar("municipality_of_registry", 80)
    val UFOfRegistry = enumeration("uf_of_registry", UF::class)
    val twin = bool("twin")
    val dateOfRegistry = date("date_of_registry")
    val DNNumber = char("dn_number", 6)

    val personId = char("person_id", 44).references(physicalPersonTable.id)
    val cpf = char("cpf", 9)
    val name = varchar("name", 120)
    val dateOfBirth = date("birthday")
    val sex = enumeration("sex", Sex::class)

    override val primaryKey = PrimaryKey(id, name = "pk_birth_certificate_id")
}