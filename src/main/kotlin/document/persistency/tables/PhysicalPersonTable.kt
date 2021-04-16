package document.persistency.tables

import document.CivilStatus
import document.Color
import document.Sex
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.`java-time`.date

object PhysicalPersonTable:IdTable<String>("physical_person") {
    override val id: Column<EntityID<String>> = char("id", 32).entityId().references(PersonTable.id)
    val cpf = char("cpf", 11)
    val birthday = date("birthday")
    val sex = enumeration("sex", Sex::class)
    val color = enumeration("color", Color::class)
    val civilStatus = enumeration("civil_status", CivilStatus::class)
    val nationality = varchar("nationality", 40)

    override val primaryKey: PrimaryKey? = PrimaryKey(id, name="pk_physical_person_id")
}