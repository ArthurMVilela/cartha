package document.controllers

import document.Notary
import document.Official
import document.PhysicalPerson
import document.persistence.tables.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Classe façade para todos os controllers do pacode de documentos
 */
class ControllersFacade {
    private val physicalPersonController = PhysicalPersonController()
    private val officialController = OfficialController()
    private val notaryController = NotaryController()

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

    private fun setupTables() {
        setupPersonTables()
        setupNotaryTable()
    }

    private fun setupPersonTables(){
        transaction {
            SchemaUtils.drop(
                PhysicalPersonTable, LegalPersonTable, OfficialTable, PersonTable
            )
            SchemaUtils.create(
                PersonTable, PhysicalPersonTable, LegalPersonTable, OfficialTable
            )
        }
    }

    private fun setupNotaryTable(){
        transaction {
            SchemaUtils.drop(
                NotaryTable
            )
            SchemaUtils.create(
                NotaryTable
            )
        }
    }
}