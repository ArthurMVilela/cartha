package document.persistency.tables.civilRegistry

import org.jetbrains.exposed.sql.Table

object observationsAndRegisteringTable : Table("observations_and_registering") {
    val id = char("id", 44).references(civilRegistryDocumentTable.id)
    val text = text("text")

    override val primaryKey = PrimaryKey(id, name = "pk_observations_and_registering_id")
}