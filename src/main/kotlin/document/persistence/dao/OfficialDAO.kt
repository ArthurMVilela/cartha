package document.persistence.dao

import document.Official
import document.persistence.tables.OfficialTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.emptySized
import org.jetbrains.exposed.sql.transactions.transaction
import persistence.CompanionDAO
import persistence.DAO
import java.lang.Exception

class OfficialDAO(id:EntityID<String>): Entity<String>(id), DAO<Official> {
    companion object : CompanionDAO<Official, OfficialDAO, String, IdTable<String>>(OfficialTable) {
        override fun insert(obj: Official): OfficialDAO {
            var r:OfficialDAO? = null
            transaction {
                try {
                    PersonDAO.insert(obj)
                    r = OfficialDAO.new(obj.id!!) {
                        cpf = obj.cpf
                        sex = obj.sex
                    }
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
            return r!!
        }

        override fun select(id: String): OfficialDAO? {
            var r:OfficialDAO? = null
            transaction {
                try {
                    r = findById(id)
                } catch (e:Exception){
                    rollback()
                    throw e
                }
            }
            return r
        }

        override fun selectMany(condition: Op<Boolean>): SizedIterable<OfficialDAO> {
            var r:SizedIterable<OfficialDAO> = emptySized()
            transaction {
                try {
                    r = find(condition)
                } catch (e:Exception){
                    rollback()
                    throw e
                }
            }
            return r
        }

        override fun selectAll(): SizedIterable<OfficialDAO> {
            var r:SizedIterable<OfficialDAO> = emptySized()
            transaction {
                try {
                    r = all()
                } catch (e:Exception){
                    rollback()
                    throw e
                }
            }
            return r
        }

        override fun update(obj: Official) {
            transaction {
                try {
                    PersonDAO.update(obj)
                    val found = findById(obj.id!!)!!
                    found.cpf = obj.cpf
                    found.sex = obj.sex
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
        }

        override fun remove(id: String) {
            transaction {
                try {
                    findById(id)?.delete()
                    PersonDAO.remove(id)
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
        }

        override fun removeWhere(condition: Op<Boolean>) {
            transaction {
                try {
                    find(condition).forEach {
                        it.delete()
                        PersonDAO.remove(it.id.value)
                    }
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
        }
    }

    var cpf by OfficialTable.cpf
    var sex by OfficialTable.sex

    override fun toType(): Official? {
        val parent = PersonDAO.select(id.value)!!
        return Official(
            id.value,
            parent.name,
            cpf,
            sex
        )
    }
}