package document.persistence.tables.civilRegistry

import document.persistence.tables.PhysicalPersonTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.`java-time`.date

object SpouseTable:IdTable<String>("spouse") {
    override val id: Column<EntityID<String>> = char("id", 32).entityId()
    val singleName = varchar("single_name", 120)
    val marriedName = varchar("married_name", 120)
    val personId = reference("person_id", PhysicalPersonTable.id)
    val birthday = date("birthday")
    val nationality = varchar("nationality", 40)

    override val primaryKey: PrimaryKey? = PrimaryKey(id, name = "pk_spouse_id")
}