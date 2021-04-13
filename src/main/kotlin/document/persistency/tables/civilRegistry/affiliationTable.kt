package document.persistency.tables.civilRegistry

import org.jetbrains.exposed.sql.Table

object affiliationTable : Table("affiliation") {
    val id = char("id", 32)
    val personId = char("person_id",32)
    val name = varchar("name", 120)
    val UF = enumeration("uf", document.UF::class).nullable()
    val municipality = varchar("municipality", 80).nullable()

    override val primaryKey = PrimaryKey(id, name = "pk_affiliation_id")
}