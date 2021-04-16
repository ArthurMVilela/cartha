package document.persistency.dao

import document.PhysicalPerson
import document.persistency.tables.PhysicalPersonTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.emptySized
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

class PhysicalPersonDAO(id: EntityID<String>):Entity<String>(id), DAO<PhysicalPerson> {
    companion object : CompanionDAO<PhysicalPerson, PhysicalPersonDAO, String, IdTable<String>>(PhysicalPersonTable) {
        override fun insert(obj: PhysicalPerson): PhysicalPersonDAO {
            var r:PhysicalPersonDAO? = null
            transaction {
                try {
                    PersonDAO.insert(obj)
                    r = PhysicalPersonDAO.new(obj.id!!) {
                        cpf = obj.cpf
                        birthday = obj.birthday
                        sex = obj.sex
                        color = obj.color
                        civilStatus = obj.civilStatus
                        nationality = obj.nationality
                    }
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
            return r!!
        }

        override fun select(id: String): PhysicalPersonDAO? {
            var r:PhysicalPersonDAO? = null
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

        override fun selectMany(condition: Op<Boolean>): SizedIterable<PhysicalPersonDAO> {
            var r:SizedIterable<PhysicalPersonDAO> = emptySized()
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

        override fun selectAll(): SizedIterable<PhysicalPersonDAO> {
            var r:SizedIterable<PhysicalPersonDAO> = emptySized()
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

        override fun update(obj: PhysicalPerson) {
            transaction {
                try {
                    PersonDAO.update(obj)
                    val found = findById(obj.id!!)!!
                    found.cpf = obj.cpf
                    found.birthday = obj.birthday
                    found.sex = obj.sex
                    found.color = obj.color
                    found.civilStatus = obj.civilStatus
                    found.nationality = obj.nationality
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

    var cpf by PhysicalPersonTable.cpf
    var birthday by PhysicalPersonTable.birthday
    var sex by PhysicalPersonTable.sex
    var color by PhysicalPersonTable.color
    var civilStatus by PhysicalPersonTable.civilStatus
    var nationality by PhysicalPersonTable.nationality

    override fun toType(): PhysicalPerson? {
        val parent = PersonDAO.select(id.value)!!
        return PhysicalPerson(
            id.value,
            parent.name,
            cpf,
            birthday,
            sex,
            color,
            civilStatus,
            nationality
        )
    }
}