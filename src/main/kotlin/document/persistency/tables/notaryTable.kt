package document.persistency.tables

import org.jetbrains.exposed.sql.Table

object notaryTable : Table ("notary") {
    val id = char("id", 32)
    val name = varchar("name", 80)
    val cnpj = char("cnpj", 14)
    val cns = char("cns", 6)

    override val primaryKey = PrimaryKey(id, name = "pk_notary_id")
}