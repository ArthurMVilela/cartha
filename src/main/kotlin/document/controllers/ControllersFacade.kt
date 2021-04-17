package document.controllers

import document.Notary
import document.Official
import document.PhysicalPerson
import document.civilRegistry.CivilRegistryDocument
import document.civilRegistry.DeathCertificate
import document.persistence.tables.*
import document.persistence.tables.civilRegistry.AffiliationTable
import document.persistence.tables.civilRegistry.CivilRegistryDocumentTable
import document.persistence.tables.civilRegistry.DeathCertificateTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Classe façade para todos os controllers do pacode de documentos
 */
class ControllersFacade {
    private val physicalPersonController = PhysicalPersonController()
    private val officialController = OfficialController()
    private val notaryController = NotaryController()
    private val deathCertificateController = DeathCertificateController()

    init {
        setupTables()
    }

    fun createPhysicalPerson(new: PhysicalPerson): PhysicalPerson = physicalPersonController.create(new)
    fun getPhysicalPerson(id: String): PhysicalPerson? = physicalPersonController.get(id)
    fun updatePhysicalPerson(id: String, new: PhysicalPerson):PhysicalPerson = physicalPersonController.update(id, new)
    fun deletePhysicalPerson(id: String) = physicalPersonController.delete(id)

    fun createOfficial(new: Official): Official = officialController.create(new)
    fun getOfficial(id: String): Official? = officialController.get(id)
    fun updateOfficial(id: String, new: Official):Official = officialController.update(id, new)
    fun deleteOfficial(id: String) = officialController.delete(id)

    fun createNotary(new: Notary): Notary = notaryController.create(new)
    fun getNotary(id: String): Notary? = notaryController.get(id)
    fun updateNotary(id: String, new: Notary):Notary = notaryController.update(id, new)
    fun deleteNotary(id: String) = notaryController.delete(id)

    fun createDeathCertificate(new: DeathCertificate): DeathCertificate = deathCertificateController.create(new)
    fun getDeathCertificate(id: String): DeathCertificate? = deathCertificateController.get(id)
    fun updateDeathCertificate(id: String, new: DeathCertificate):DeathCertificate = deathCertificateController.update(id, new)
    fun deleteDeathCertificate(id: String) = deathCertificateController.delete(id)

    private fun setupTables() {
        setupPersonTables()
        setupNotaryTable()
        setupDocumentTables()
    }

    private fun setupPersonTables(){
        transaction {
            SchemaUtils.create(
                PersonTable, PhysicalPersonTable, LegalPersonTable, OfficialTable
            )
        }
    }

    private fun setupNotaryTable(){
        transaction {
            SchemaUtils.create(
                NotaryTable
            )
        }
    }

    private fun setupDocumentTables() {
        transaction {
            SchemaUtils.create(
                AffiliationTable,
                DocumentTable, CivilRegistryDocumentTable, DeathCertificateTable
            )
        }
    }
}