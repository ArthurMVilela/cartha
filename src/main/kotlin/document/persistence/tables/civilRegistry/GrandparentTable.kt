package document.persistence.tables.civilRegistry

import document.UF
import document.civilRegistry.GrandparentType
import document.persistence.tables.PersonTable
import document.persistence.tables.PhysicalPersonTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object GrandparentTable:IdTable<String>("grandparent") {
    override val id: Column<EntityID<String>> = char("id", 32).entityId()
    val documentId = reference("document_id", CivilRegistryDocumentTable.id)
    val personId = reference("person_id", PhysicalPersonTable.id)
    val name = varchar("name", 120)
    val type = enumeration("type", GrandparentType::class)
    val uf = enumeration("uf", UF::class).nullable()
    val municipality = varchar("municipality", 80).nullable()

    override val primaryKey: PrimaryKey? = PrimaryKey(id, name = "pk_grandparent_id")
}