package document.persistence.tables.civilRegistry

import document.persistence.tables.DocumentTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import java.util.*

object CivilRegistryDocumentTable:IdTable<UUID>("civil_registry_document") {
    override val id: Column<EntityID<UUID>> = reference("id", DocumentTable.id)

    val registrationNumber = char("registration_number", 120)
}