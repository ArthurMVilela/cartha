package document.persistency.tables.civilRegistry

import org.jetbrains.exposed.sql.Table

object spouseAffiliationTable : Table("spouse_affiliation") {
    val id = integer("id").autoIncrement()
    val spouseId = char("spouse_id", 44).references(spouseTable.id)
    val affiliationId = char("affiliation_id", 32).references(affiliationTable.id)

    override val primaryKey = PrimaryKey(id, name = "pk_spouse_affiliation_id")
}