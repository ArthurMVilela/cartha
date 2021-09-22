package document.persistence.tables.civilRegistry.marriage

import document.civilRegistry.marriage.MatrimonialRegime
import document.persistence.tables.civilRegistry.CivilRegistryDocumentTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.`java-time`.date
import java.util.*

object MarriageCertificateTable:IdTable<UUID>("marriage_certificate") {
    override val id: Column<EntityID<UUID>> = reference("id", CivilRegistryDocumentTable.id)

    val dateOfRegistry = date("date_of_registry")
    val matrimonialRegime = enumeration("matrimonial_regime", MatrimonialRegime::class)
}