package newDocument.persistence.tables.address

import newDocument.address.UF
import org.jetbrains.exposed.dao.id.UUIDTable

object MunicipalityTable:UUIDTable("municipality") {
    val name = varchar("name", 120)
    val uf = enumeration("uf", UF::class)
}