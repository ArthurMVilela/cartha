package document.persistence.tables

import org.jetbrains.exposed.dao.id.UUIDTable

object NotaryTable:UUIDTable("notary") {
    val name = varchar("name", 120)
    val cnpj = char("cnpj",14).uniqueIndex().uniqueIndex()
    val cns = char("cns", 6)
}