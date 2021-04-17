package document.persistence.tables

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object LegalPersonTable:IdTable<String>("legal_person") {
    override val id: Column<EntityID<String>> = char("id", 32).entityId().references(PersonTable.id)
    val cnpj = char("cnpj", 13)

    override val primaryKey: PrimaryKey?
        get() = super.primaryKey
}