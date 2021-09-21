package newDocument.persistence.tables.civilRegistry

import document.persistence.tables.PhysicalPersonTable
import newDocument.persistence.tables.address.MunicipalityTable
import org.jetbrains.exposed.dao.id.UUIDTable

object AffiliationTable:UUIDTable("affiliation") {
    val personId = optReference("person_id", PhysicalPersonTable.id)
    val documentId = reference("document_id", CivilRegistryDocumentTable.id)
    val name = varchar("name", 140)
    val municipalityId = reference("municipality_id", MunicipalityTable.id)
}