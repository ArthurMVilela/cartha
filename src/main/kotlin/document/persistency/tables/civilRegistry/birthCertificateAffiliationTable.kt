package document.persistency.tables.civilRegistry

import org.jetbrains.exposed.sql.Table

object birthCertificateAffiliationTable : Table("birth_certificate_affiliation") {
    val id = integer("id").autoIncrement()
    val birthCertificateId = char("birth_certificate_id", 44).references(birthCertificateTable.id)
    val affiliationId = char("affiliation_id", 32).references(affiliationTable.id)

    override val primaryKey = PrimaryKey(id, name = "pk_birth_certificate_affiliation_id")
}