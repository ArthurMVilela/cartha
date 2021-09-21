package newDocument.persistence.tables.civilRegistry.birth

import org.jetbrains.exposed.dao.id.UUIDTable

object TwinTable:UUIDTable("twin") {
    val birthCertificateId = reference("birth_certificate_id", BirthCertificateTable.id)
    val twinBirthCertificateId = reference("twin_birth_certificate_id", BirthCertificateTable.id)
    val registrationNumber = char("registration_number")
    val name = varchar("name", 140)
}