package newDocument.persistence.tables.address

import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object AddressTable:UUIDTable("address") {
    val name = varchar("name", 120).nullable()
    val street = varchar("street", 120)
    val number = varchar("number",5)
    val complement = varchar("complement", 40).nullable()
    val neighborhood = varchar("neighborhood", 60)
    val municipalityId = reference("municipality_id", MunicipalityTable.id)
}