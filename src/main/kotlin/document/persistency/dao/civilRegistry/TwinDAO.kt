package document.persistency.dao.civilRegistry

import document.civilRegistry.Twin
import document.persistency.dao.CompanionDAO
import document.persistency.dao.DAO
import document.persistency.tables.civilRegistry.TwinTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SizedIterable

class TwinDAO(id:EntityID<String>):Entity<String>(id), DAO<Twin> {
    companion object : CompanionDAO<Twin, TwinDAO, String, IdTable<String>>(TwinTable){
        override fun insert(obj: Twin): TwinDAO {
            TODO("Not yet implemented")
        }

        override fun select(id: String): TwinDAO? {
            TODO("Not yet implemented")
        }

        override fun selectMany(condition: Op<Boolean>): SizedIterable<TwinDAO> {
            TODO("Not yet implemented")
        }

        override fun selectAll(): SizedIterable<TwinDAO> {
            TODO("Not yet implemented")
        }

        override fun update(obj: Twin) {
            TODO("Not yet implemented")
        }

        override fun remove(id: String) {
            TODO("Not yet implemented")
        }

        override fun removeWhere(condition: Op<Boolean>) {
            TODO("Not yet implemented")
        }
    }

    override fun toType(): Twin? {
        TODO("Not yet implemented")
    }
}