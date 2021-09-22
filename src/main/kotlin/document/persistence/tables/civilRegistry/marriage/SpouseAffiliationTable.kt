package document.persistence.tables.civilRegistry.marriage

import document.persistence.tables.civilRegistry.AffiliationTable
import org.jetbrains.exposed.sql.Table

object SpouseAffiliationTable:Table("spouse_affiliation") {
    val spouseId = reference("spouse_id", SpouseTable.id)
    val affiliationId = reference("affiliation_id", AffiliationTable.id)
}