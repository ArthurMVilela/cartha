package document.persistency.tables

import document.Sex
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object OfficialTable : IdTable<String>("official") {
    override val id: Column<EntityID<String>> = char("id", 32).entityId().references(PersonTable.id)
    val cpf = char("cpf", 11)
    val sex = enumeration("sex", Sex::class)

    override val primaryKey: PrimaryKey? = PrimaryKey(id, name = "pk_official_id")
}