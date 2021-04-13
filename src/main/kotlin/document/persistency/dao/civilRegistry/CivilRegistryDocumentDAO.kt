package document.persistency.dao.civilRegistry

import document.civilRegistry.CivilRegistryDocument
import document.persistency.dao.DAO
import org.jetbrains.exposed.sql.Op

class CivilRegistryDocumentDAO: DAO<CivilRegistryDocument, String> {
    override fun insert(obj: CivilRegistryDocument) {
        TODO("Not yet implemented")
    }

    override fun select(id: String): CivilRegistryDocument? {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>): List<CivilRegistryDocument> {
        TODO("Not yet implemented")
    }

    override fun selectAll(): List<CivilRegistryDocument> {
        TODO("Not yet implemented")
    }

    override fun update(oldId: String, new: CivilRegistryDocument) {
        TODO("Not yet implemented")
    }

    override fun delete(id: String) {
        TODO("Not yet implemented")
    }

    override fun deleteWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }
}