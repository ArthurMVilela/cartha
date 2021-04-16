package document.persistency.tables.civilRegistry

import document.Sex
import document.UF
import document.persistency.tables.PhysicalPersonTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object AffiliationTable : IdTable<String>("affiliation") {
    override val id: Column<EntityID<String>> = char("id", 32).entityId()
    val personId = reference("person_id", PhysicalPersonTable.id)
    val documentId = reference("document_id", CivilRegistryDocumentTable)
    val name = varchar("name", 120)
    val uf = enumeration("uf", UF::class).nullable()
    val municipality = varchar("municipality", 80).nullable()

    override val primaryKey: PrimaryKey? = PrimaryKey(id, name = "pk_affiliation_id")
}