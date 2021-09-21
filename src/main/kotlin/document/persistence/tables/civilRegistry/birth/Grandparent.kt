package document.persistence.tables.civilRegistry.birth

import document.persistence.tables.person.PhysicalPersonTable
import document.civilRegistry.birth.GrandparentType
import document.persistence.tables.address.MunicipalityTable
import org.jetbrains.exposed.dao.id.UUIDTable

object Grandparent:UUIDTable("grandparent") {
    val personId = optReference("person_id", PhysicalPersonTable.id)
    val birthCertificateId = reference("birth_certificate_id", BirthCertificateTable.id)
    val name = varchar("name", 140)
    val type = enumeration("type", GrandparentType::class)
    val municipalityId = reference("municipality_id", MunicipalityTable.id)
}