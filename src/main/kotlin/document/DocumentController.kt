package document

import document.persistency.tables.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Classe controller para o pacode de documentos
 */
class DocumentController {
    init {
        setupTables()
    }
    private fun setupTables() {
        transaction {
            SchemaUtils.create(
                personTable, physicalPersonTable, legalPersonTable, officialTable
            )
        }
    }
}