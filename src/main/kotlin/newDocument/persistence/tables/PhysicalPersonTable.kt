package newDocument.persistence.tables

import newDocument.person.CivilStatus
import newDocument.person.Color
import newDocument.person.Sex
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.`java-time`.date
import java.util.*

object PhysicalPersonTable:IdTable<UUID>("physical_person") {
    override val id: Column<EntityID<UUID>> = reference("id", PersonTable.id)

    val cpf = char("cpf", 11).uniqueIndex()
    val birthday = date("birthday")
    val sex = enumeration("sex", Sex::class)
    val color = enumeration("color", Color::class)
    val civilStatus = enumeration("civil_status", CivilStatus::class)
    val nationality = varchar("nationality",60)


}