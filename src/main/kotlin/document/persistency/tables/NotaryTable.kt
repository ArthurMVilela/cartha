package document.persistency.tables

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object NotaryTable : IdTable<String>("notary") {
    override val id: Column<EntityID<String>> = char("id", 32).entityId()
    val name = varchar("name", 80)
    val cnpj = char("cnpj", 14)
    val cns = char("cns", 6)

    override val primaryKey: PrimaryKey? = PrimaryKey(id, name = "pk_notary_id")
}