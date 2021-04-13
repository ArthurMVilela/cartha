package document.persistency.tables

import document.CivilStatus
import document.Color
import document.Sex
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.date

object physicalPersonTable : Table("physical_person") {
    val id = char("id",32).references(personTable.id)
    val cpf = char("cpf", 11)
    val birthday = date("birthday")
    val sex = enumeration("sex", Sex::class)
    val color = enumeration("color", Color::class)
    val civilStatus = enumeration("civil_status", CivilStatus::class)
    val nationality = varchar("nationality", 30)

    override val primaryKey = PrimaryKey(id, name = "pk_physical_person_id")
}