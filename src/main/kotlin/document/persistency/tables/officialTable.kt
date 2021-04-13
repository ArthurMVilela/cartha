package document.persistency.tables

import document.Sex
import org.jetbrains.exposed.sql.Table

object officialTable : Table("official") {
    val id = char("id", 32).references(personTable.id)
    val cpf = char("cpf", 11)
    val sex = enumeration("sex", Sex::class)

    override val primaryKey = PrimaryKey(id, name = "pk_official_id")
}