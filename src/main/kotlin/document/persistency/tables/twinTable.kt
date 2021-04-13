package document.persistency.tables

import org.jetbrains.exposed.sql.Table

object twinTable : Table("twin") {
    val id = char("id", 32)
    val birthCertificateId = char("id", 44).references(birthCertificateTable.id)
    val registration = char("registration", 32)
    val name = varchar("name", 120)

    override val primaryKey = PrimaryKey(id, name = "pk_twin_id")
}