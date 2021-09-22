package document.persistence.tables.civilRegistry.marriage

import document.persistence.tables.person.PhysicalPersonTable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.`java-time`.date

object SpouseTable:UUIDTable("spouse") {
    val marriageCertificateId = reference("marriage_certificate_id", MarriageCertificateTable.id)
    val personId = optReference("person_id", PhysicalPersonTable.id)
    val singleName = varchar("single_name", 140)
    val marriedName = varchar("married_name", 140)
    val birthday = date("birthday")
    val nationality = varchar("nationality", 60)
}