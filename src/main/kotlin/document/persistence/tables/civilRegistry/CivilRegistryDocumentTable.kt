package document.persistence.tables.civilRegistry

import document.persistence.tables.DocumentTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object CivilRegistryDocumentTable:IdTable<String>("civil_registry_document") {
    override val id: Column<EntityID<String>> = char("id", 44).entityId().references(DocumentTable.id)
    val registration = char("registering", 32)

    override val primaryKey: PrimaryKey? = PrimaryKey(id, name = "pk_civil_registry_document_id")
}