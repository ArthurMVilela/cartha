package document.persistence.tables.civilRegistry

import document.civilRegistry.MatrimonialRegime
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.`java-time`.date

object MarriageCertificateTable:IdTable<String>("marriage_certificate") {
    override val id: Column<EntityID<String>> = char("id", 44).entityId().references(CivilRegistryDocumentTable.id)
    val firstSpouseId = reference("first_spouse_id", SpouseTable.id)
    val secondSpouseId = reference("second_spouse_id", SpouseTable.id)
    val dateOfRegistry = date("date_of_registry")
    val matrimonialRegime = enumeration("matrimonial_regime", MatrimonialRegime::class)

    override val primaryKey: PrimaryKey? = PrimaryKey(id, name = "pk_marriage_certificate_id")
}