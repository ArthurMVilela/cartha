package document.persistency.tables.civilRegistry

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object BirthCertificateAffiliationTable:IdTable<Int>("birth_certificate_affiliation") {
    override val id: Column<EntityID<Int>> = integer("id").entityId().autoIncrement()
    val birthCertificateId = reference("birth_certificate_id", BirthCertificateAffiliationTable.id)
    val affiliationId = reference("affiliation_id", AffiliationTable.id)

    override val primaryKey: PrimaryKey? = PrimaryKey(id, name = "pk_birth_certificate_affiliation_id")
}