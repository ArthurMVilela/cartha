package document.persistency.tables.civilRegistry

import document.civilRegistry.GrandparentType
import org.jetbrains.exposed.sql.Table

object grandparentTable : Table("grandparent") {
    val id = char("id", 32)
    val personId = char("person_id",32)
    val name = varchar("name", 120)
    val type = enumeration("type", GrandparentType::class)
    val UF = enumeration("uf", document.UF::class).nullable()
    val municipality = varchar("municipality", 80).nullable()

    override val primaryKey = PrimaryKey(id, name = "pk_grandparent_id")
}