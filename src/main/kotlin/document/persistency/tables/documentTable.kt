package document.persistency.tables

import document.*
import org.jetbrains.exposed.sql.Table

object documentTable : Table("document") {
    val id = char("id", 44)
    val status = enumeration("status", DocumentStatus::class)
    val officialId = char("official_id", 32)
    val notaryId = char("notaryId", 32)

    override val primaryKey = PrimaryKey(id, name = "pk_document_id")
}