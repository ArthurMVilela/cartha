package document.persistence.tables.civilRegistry

import document.persistence.tables.person.PhysicalPersonTable
import document.persistence.tables.address.MunicipalityTable
import org.jetbrains.exposed.dao.id.UUIDTable

object AffiliationTable:UUIDTable("affiliation") {
    val personId = optReference("person_id", PhysicalPersonTable.id)
    val cpf = char("cpf", 11)
    val documentId = reference("document_id", CivilRegistryDocumentTable.id)
    val name = varchar("name", 140)
    val municipalityId = reference("municipality_id", MunicipalityTable.id)
}