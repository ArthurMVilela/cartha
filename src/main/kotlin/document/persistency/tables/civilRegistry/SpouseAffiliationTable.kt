package document.persistency.tables.civilRegistry

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object SpouseAffiliationTable:IdTable<Int>("spouse_affiliation_table") {
    override val id: Column<EntityID<Int>> = integer("id").entityId().autoIncrement()
    val spouseId = reference("spouse_id", SpouseTable)
    val affiliationId = reference("affiliation_id", AffiliationTable)

    override val primaryKey: PrimaryKey? = PrimaryKey(id, name = "pk_spouse_affiliation_table_id")
}