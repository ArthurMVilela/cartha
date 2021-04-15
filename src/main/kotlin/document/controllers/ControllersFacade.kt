package document.controllers

import document.LegalPerson
import document.Notary
import document.Official
import document.PhysicalPerson
import document.persistency.tables.*
import document.persistency.tables.civilRegistry.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Classe façade para todos os controllers do pacode de documentos
 */
class ControllersFacade {
    init {
        setupTables()
    }
    private fun setupTables() {
        transaction {
//            SchemaUtils.create(
//                personTable, physicalPersonTable, legalPersonTable, officialTable,
//                notaryTable,
//                documentTable, civilRegistryDocumentTable,
//                affiliationTable, spouseTable, grandparentTable,
//                birthCertificateTable, twinTable,
//                deathCertificateTable, marriageCertificateTable
//            )
        }
    }
}