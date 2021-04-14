package document.controllers

import document.PhysicalPerson
import document.persistency.tables.*
import document.persistency.tables.civilRegistry.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Classe façade para todos os controllers do pacode de documentos
 */
class ControllersFacade {
    val personController = PersonController()

    fun createPhysicalPerson(person: PhysicalPerson): PhysicalPerson {
        return personController.createPhysicalPerson(person)
    }
    fun getPhysicalPerson(id:String): PhysicalPerson? {
        return personController.getPhysicalPerson(id)
    }
    fun updatePhysicalPerson(id: String, new: PhysicalPerson) {
        personController.updatePhysicalPerson(id, new)
    }
    fun deletePhysicalPerson(id: String) {
        personController.deletePhysicalPerson(id)
    }

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