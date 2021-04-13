package document.persistency.tables.civilRegistry

import org.jetbrains.exposed.sql.Table

object birthCertificateGrandparentTable : Table("birth_certificate_grandparent") {
    val id = integer("id").autoIncrement()
    val birthCertificateId = char("birth_certificate_id", 44).references(birthCertificateTable.id)
    val grandparentId = char("grandparent_id", 32).references(grandparentTable.id)

    override val primaryKey = PrimaryKey(id, name = "pk_birth_certificate_grandparent_id")
}