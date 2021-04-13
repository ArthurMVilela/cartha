package document.persistency.tables

import org.jetbrains.exposed.sql.Table

object personTable : Table("person"){
    val id = char("id",32)
    val name = varchar("name", 120)

    override val primaryKey = PrimaryKey(id, name = "pk_person_id")
}