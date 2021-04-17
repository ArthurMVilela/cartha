package document.persistency.tables.civilRegistry

import document.civilRegistry.BirthCertificate
import document.persistency.tables.PersonTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object TwinTable:IdTable<String>("twin") {
    override val id: Column<EntityID<String>> = char("id", 32).entityId()
    val birthCertificateId = reference("birth_certificate_id", BirthCertificateTable.id)
    val twinBirthCertificateId = reference("twin_birth_certificate_id", BirthCertificateTable.id)
    val registration = char("registering", 32)
    val name = varchar("name", 120)

    override val primaryKey: PrimaryKey? = PrimaryKey(id, name="pk_twin_id")
}