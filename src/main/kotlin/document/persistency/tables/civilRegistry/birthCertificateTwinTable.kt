package document.persistency.tables.civilRegistry

import org.jetbrains.exposed.sql.Table

object birthCertificateTwinTable : Table("birth_certificate_twin") {
    val id = integer("id").autoIncrement()
    val birthCertificateId = char("birth_certificate_id", 44).references(birthCertificateTable.id)
    val twinId = char("twin_id", 32).references(twinTable.id)

    override val primaryKey = PrimaryKey(id, name = "pk_birth_certificate_twin_id")
}