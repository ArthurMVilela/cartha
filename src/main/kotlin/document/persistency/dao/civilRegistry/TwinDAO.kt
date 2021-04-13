package document.persistency.dao.civilRegistry

import document.civilRegistry.Twin
import document.persistency.dao.DAO
import org.jetbrains.exposed.sql.Op

class TwinDAO:DAO<Twin, String> {
    override fun insert(obj: Twin) {
        TODO("Not yet implemented")
    }

    override fun select(id: String): Twin? {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>): List<Twin> {
        TODO("Not yet implemented")
    }

    override fun selectAll(): List<Twin> {
        TODO("Not yet implemented")
    }

    override fun update(oldId: String, new: Twin) {
        TODO("Not yet implemented")
    }

    override fun delete(id: String) {
        TODO("Not yet implemented")
    }

    override fun deleteWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }
}