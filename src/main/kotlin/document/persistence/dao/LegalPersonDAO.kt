package document.persistence.dao

import document.LegalPerson
import document.persistence.tables.LegalPersonTable
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

class LegalPersonDAO(id:EntityID<String>):Entity<String>(id), DAO<LegalPerson> {
    companion object : CompanionDAO<LegalPerson, LegalPersonDAO, String, IdTable<String>>(LegalPersonTable) {
        override fun insert(obj: LegalPerson): LegalPersonDAO {
            var r:LegalPersonDAO? = null
            transaction {
                try {
                    PersonDAO.insert(obj)
                    r = LegalPersonDAO.new(obj.id!!) {
                        cnpj = obj.cnpj
                    }
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
            return r!!
        }

        override fun select(id: String): LegalPersonDAO? {
            var r:LegalPersonDAO? = null
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

        override fun selectMany(condition: Op<Boolean>): SizedIterable<LegalPersonDAO> {
            var r:SizedIterable<LegalPersonDAO> = emptySized()
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

        override fun selectAll(): SizedIterable<LegalPersonDAO> {
            var r:SizedIterable<LegalPersonDAO> = emptySized()
            transaction {
                try {
                    r = LegalPersonDAO.all()
                } catch (e:Exception){
                    rollback()
                    throw e
                }
            }
            return r
        }

        override fun update(obj: LegalPerson) {
            transaction {
                try {
                    PersonDAO.update(obj)
                    val found = findById(obj.id!!)!!
                    found.cnpj = obj.cnpj
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

    var cnpj by LegalPersonTable.cnpj

    override fun toType(): LegalPerson? {
        val parent = PersonDAO.select(id.value)!!
        return LegalPerson(
            id.value,
            parent.name,
            cnpj
        )
    }
}