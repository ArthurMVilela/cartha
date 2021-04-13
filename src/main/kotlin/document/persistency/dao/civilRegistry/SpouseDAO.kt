package document.persistency.dao.civilRegistry

import document.civilRegistry.Spouse
import document.persistency.dao.DAO
import org.jetbrains.exposed.sql.Op

class SpouseDAO:DAO<Spouse,String> {
    override fun insert(obj: Spouse) {
        TODO("Not yet implemented")
    }

    override fun select(id: String): Spouse? {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>): List<Spouse> {
        TODO("Not yet implemented")
    }

    override fun selectAll(): List<Spouse> {
        TODO("Not yet implemented")
    }

    override fun update(oldId: String, new: Spouse) {
        TODO("Not yet implemented")
    }

    override fun delete(id: String) {
        TODO("Not yet implemented")
    }

    override fun deleteWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }
}