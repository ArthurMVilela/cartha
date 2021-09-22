package document.persistence.tables.civilRegistry

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.`java-time`.datetime

object RegisteringTable:UUIDTable("registering") {
    val documentId = reference("document_id", CivilRegistryDocumentTable.id)
    val timestamp = datetime("timestamp")
    val text = text("text")
}