package newDocument.persistence

import newDocument.persistence.tables.NotaryTable
import newDocument.persistence.tables.OfficialTable
import newDocument.persistence.tables.PersonTable
import newDocument.persistence.tables.PhysicalPersonTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseInitializer {
    companion object {
        fun initialize() {
            transaction {
                SchemaUtils.create(
                    NotaryTable,
                    PersonTable, PhysicalPersonTable, OfficialTable
                )
            }
        }
    }
}