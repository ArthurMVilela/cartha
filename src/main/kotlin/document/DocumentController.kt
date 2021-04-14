package document

import document.persistency.tables.*
import document.persistency.tables.civilRegistry.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Classe controller para o pacode de documentos
 */
class DocumentController {
    val personController = PersonController()

    init {
        setupTables()
    }
    private fun setupTables() {
        transaction {
            SchemaUtils.create(
                personTable, physicalPersonTable, legalPersonTable, officialTable,
                notaryTable,
                documentTable, civilRegistryDocumentTable,
                affiliationTable, spouseTable, grandparentTable,
                birthCertificateTable, twinTable,
                deathCertificateTable, marriageCertificateTable
            )
        }
    }
}