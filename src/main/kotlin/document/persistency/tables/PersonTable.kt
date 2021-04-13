package document.persistency.tables

import document.CivilStatus
import document.Color
import document.Sex
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.date

object PersonTable : Table("person"){
    val id = char("id",32)
    val name = varchar("name", 120)

    override val primaryKey = PrimaryKey(PhysicalPersonTable.id, name = "pk_person_id")
}

object PhysicalPersonTable : Table("physical_person") {
    val id = char("id",32).references(PersonTable.id)
    val cpf = char("cpf", 11)
    val birthday = date("birthday")
    val sex = enumeration("sex", Sex::class)
    val color = enumeration("color", Color::class)
    val civilStatus = enumeration("civil_status", CivilStatus::class)
    val nationality = varchar("nationality", 30)

    override val primaryKey = PrimaryKey(id, name = "pk_physical_person_id")
}

object LegalPersonTable : Table("legal_person") {
    val id = char("id", 32).references(PersonTable.id)
    val cpnj = char("cnpj", 14)

    override val primaryKey = PrimaryKey(PhysicalPersonTable.id, name = "pk_legal_person_id")
}

object OfficialTable : Table("official") {
    val id = char("id", 32).references(PersonTable.id)
    val cpf = char("cpf", 11)
    val sex = enumeration("sex", Sex::class)

    override val primaryKey = PrimaryKey(PhysicalPersonTable.id, name = "pk_official_id")
}