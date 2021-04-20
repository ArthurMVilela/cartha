package document.persistence.dao

import document.Person
import document.persistence.tables.PersonTable
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

class PersonDAO(id:EntityID<String>):Entity<String>(id), DAO<Person> {
    companion object : CompanionDAO<Person, PersonDAO, String, IdTable<String>>(PersonTable) {
        override fun insert(obj: Person): PersonDAO {
            var r:PersonDAO? = null
            transaction {
                try {
                    r = new(obj.id!!) {
                        name = obj.name
                    }
                } catch (e:Exception) {
                    rollback()
                    throw e
                }
            }
            return r!!
        }

        override fun select(id: String): PersonDAO? {
            var r:PersonDAO? = null
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

        override fun selectMany(condition: Op<Boolean>): SizedIterable<PersonDAO> {
            var r:SizedIterable<PersonDAO> = emptySized()
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

        override fun selectAll(): SizedIterable<PersonDAO> {
            var r:SizedIterable<PersonDAO> = emptySized()
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

        override fun update(obj: Person) {
            transaction {
                try {
                    val found = findById(obj.id!!)!!
                    found.name = obj.name
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
                    }
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
        }
    }

    var name by PersonTable.name

    override fun toType(): Person? {
        return object : Person() {
            override var id: String? = this@PersonDAO.id.value
            override val name: String = this@PersonDAO.name
        }
    }
}