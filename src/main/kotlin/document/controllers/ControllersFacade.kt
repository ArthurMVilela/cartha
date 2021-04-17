package document.controllers

import document.Official
import document.PhysicalPerson
import document.persistence.tables.LegalPersonTable
import document.persistence.tables.OfficialTable
import document.persistence.tables.PersonTable
import document.persistence.tables.PhysicalPersonTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Classe façade para todos os controllers do pacode de documentos
 */
class ControllersFacade {
    private val physicalPersonController = PhysicalPersonController()
    private val officialController = OfficialController()

    init {
        setupTables()
    }

    fun createPhysicalPerson(person: PhysicalPerson): PhysicalPerson = physicalPersonController.create(person)
    fun getPhysicalPerson(id: String): PhysicalPerson? = physicalPersonController.get(id)
    fun updatePhysicalPerson(id: String, new: PhysicalPerson):PhysicalPerson = physicalPersonController.update(id, new)
    fun deletePhysicalPerson(id: String) = physicalPersonController.delete(id)

    fun createOfficial(person: Official): Official = officialController.create(person)
    fun getOfficial(id: String): Official? = officialController.get(id)
    fun updateOfficial(id: String, new: Official):Official = officialController.update(id, new)
    fun deleteOfficial(id: String) = officialController.delete(id)

    private fun setupTables() {
        setupPersonTables()
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
}