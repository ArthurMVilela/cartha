package document.persistence.tables

import document.DocumentStatus
import document.persistence.tables.person.OfficialTable
import org.jetbrains.exposed.dao.id.UUIDTable

object DocumentTable:UUIDTable("document") {
    val status = enumeration("status", DocumentStatus::class)
    val officialId = reference("official_id", OfficialTable.id)
    val notaryId = reference("notary_id", NotaryTable.id)
    val hash = char("hash", 44)
}