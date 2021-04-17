package document.persistence.tables

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object PersonTable:IdTable<String>("person") {
    override val id: Column<EntityID<String>> = char("id", 32).entityId()
    val name = varchar("name", 120)

    override val primaryKey: PrimaryKey? = PrimaryKey(id, name="pk_person_id")
}