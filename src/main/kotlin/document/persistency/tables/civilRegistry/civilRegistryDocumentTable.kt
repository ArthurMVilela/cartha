package document.persistency.tables.civilRegistry

import document.persistency.tables.documentTable
import org.jetbrains.exposed.sql.Table

object civilRegistryDocumentTable : Table("civil_registry_document") {
    val id = char("id", 44).references(documentTable.id)
    val registration = char("registration", 32)

    override val primaryKey = PrimaryKey(id, name = "pk_civil_registry_document_id")
}