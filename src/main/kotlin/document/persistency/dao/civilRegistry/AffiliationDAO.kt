package document.persistency.dao.civilRegistry

import document.civilRegistry.Affiliation
import document.persistency.dao.DAO
import org.jetbrains.exposed.sql.Op

class AffiliationDAO:DAO<Affiliation, String> {
    override fun insert(obj: Affiliation) {
        TODO("Not yet implemented")
    }

    override fun select(id: String): Affiliation? {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>): List<Affiliation> {
        TODO("Not yet implemented")
    }

    override fun selectAll(): List<Affiliation> {
        TODO("Not yet implemented")
    }

    override fun update(oldId: String, new: Affiliation) {
        TODO("Not yet implemented")
    }

    override fun delete(id: String) {
        TODO("Not yet implemented")
    }

    override fun deleteWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }
}