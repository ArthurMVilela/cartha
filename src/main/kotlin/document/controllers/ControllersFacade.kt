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
    val personController = PersonController()
    val notaryController = NotaryController()

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

    fun createLegalPerson(person: LegalPerson): LegalPerson {
        return personController.createLegalPerson(person)
    }
    fun getLegalPerson(id:String): LegalPerson? {
        return personController.getLegalPerson(id)
    }
    fun updateLegalPerson(id: String, new: LegalPerson) {
        personController.updateLegalPerson(id, new)
    }
    fun deleteLegalPerson(id: String) {
        personController.deleteLegalPerson(id)
    }

    fun createOfficial(person: Official): Official {
        return personController.createOfficial(person)
    }
    fun getOfficial(id:String): Official? {
        return personController.getOfficial(id)
    }
    fun updateOfficial(id: String, new: Official) {
        personController.updateOfficial(id, new)
    }
    fun deleteOfficial(id: String) {
        personController.deleteOfficial(id)
    }

    fun createNotary(notary: Notary): Notary {
        return notaryController.createNotary(notary)
    }
    fun getNotary(id:String): Notary? {
        return notaryController.getNotary(id)
    }
    fun updateNotary(id: String, new: Notary) {
        return notaryController.updateNotary(id, new)
    }
    fun deleteNotary(id: String) {
        return notaryController.deleteNotary(id)
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