package document.persistency.tables

import document.civilRegistry.MatrimonialRegime
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.date

object marriageCertificateTable : Table("marriage_certificate") {
    val id = char("id", 44).references(civilRegistryDocumentTable.id)
    val firstSpouseId = char("first_spouse_id", 32)
    val secondSpouseId = char("first_spouse_id", 32)
    val dateOfRegistry = date("date_of_registry")
    val matrimonialRegime = enumeration("matrimonial_regime", MatrimonialRegime::class)

    override val primaryKey = PrimaryKey(id, name = "pk_marriage_certificate_id")
}