package document.persistence.tables.civilRegistry

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object RegisteringTable:IdTable<String>("registering") {
    override val id: Column<EntityID<String>> = char("id", 32).entityId()
    val documentID = reference("document_id", CivilRegistryDocumentTable.id)
    val text = text("text")

    override val primaryKey: PrimaryKey? = PrimaryKey(id, name = "pk_registering_id")
}