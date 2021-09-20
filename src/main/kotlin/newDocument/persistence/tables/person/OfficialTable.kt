package newDocument.persistence.tables.person

import newDocument.persistence.tables.NotaryTable
import newDocument.person.Sex
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import java.util.*

object OfficialTable:IdTable<UUID>("official") {
    override val id: Column<EntityID<UUID>> = reference("id", PersonTable.id)
    val cpf = char("cpf", 11).uniqueIndex()
    val sex = enumeration("sex", Sex::class)
    val notaryId = reference("notary_id", NotaryTable.id)
}