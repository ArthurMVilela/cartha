package document.persistency.dao.civilRegistry

import document.civilRegistry.Grandparent
import document.persistency.dao.DAO
import org.jetbrains.exposed.sql.Op

class GrandparentDAO:DAO<Grandparent,String> {
    override fun insert(obj: Grandparent) {
        TODO("Not yet implemented")
    }

    override fun select(id: String): Grandparent? {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>): List<Grandparent> {
        TODO("Not yet implemented")
    }

    override fun selectAll(): List<Grandparent> {
        TODO("Not yet implemented")
    }

    override fun update(oldId: String, new: Grandparent) {
        TODO("Not yet implemented")
    }

    override fun delete(id: String) {
        TODO("Not yet implemented")
    }

    override fun deleteWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }
}