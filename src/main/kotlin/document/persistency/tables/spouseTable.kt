package document.persistency.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.date

object spouseTable : Table("spouse") {
    val id = char("first_spouse_id", 32)
    val singleName = varchar("name", 120)
    val marriedName = varchar("name", 120)
    val personId = char("id", 32)
    val birthday = date("birthday")
    val nationality = varchar("nationality", 30)
}