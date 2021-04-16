package document.persistency.tables

import document.DocumentStatus
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object DocumentTable:IdTable<String>("document") {
    override val id: Column<EntityID<String>> = char("id", 44).entityId()
    val status = enumeration("status", DocumentStatus::class)
    val officialId = reference("official_id", OfficialTable.id)
    val notaryId = reference("notary_id", NotaryTable.id)

    override val primaryKey: PrimaryKey? = PrimaryKey(id, name="pk_document_id")
}