package document.persistency.dao.civilRegistry

import document.civilRegistry.Registering
import document.persistency.dao.*
import document.persistency.tables.civilRegistry.RegisteringTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.emptySized
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

class RegisteringDAO(id: EntityID<String>): Entity<String>(id), DAO<Registering> {
    companion object : CompanionDAO<Registering, RegisteringDAO, String, IdTable<String>>(RegisteringTable) {
        override fun insert(obj: Registering): RegisteringDAO {
            var r: RegisteringDAO? = null
            transaction {
                try {
                    r = new(obj.id!!) {
                        documentId = DocumentDAO.select(obj.documentID)!!.id
                        text = obj.text
                    }
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
            return r!!
        }

        override fun select(id: String): RegisteringDAO? {
            var r:RegisteringDAO? = null
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

        override fun selectMany(condition: Op<Boolean>): SizedIterable<RegisteringDAO> {
            var r:SizedIterable<RegisteringDAO> = emptySized()
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

        override fun selectAll(): SizedIterable<RegisteringDAO> {
            var r:SizedIterable<RegisteringDAO> = emptySized()
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

        override fun update(obj: Registering) {
            transaction {
                try {
                    val found = findById(obj.id!!)!!
                    found.documentId = CivilRegistryDocumentDAO.select(obj.documentID)!!.id
                    found.text = obj.text
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

    var documentId by RegisteringTable.documentID
    var text by RegisteringTable.text

    override fun toType(): Registering? {
        return Registering(
            id.value,
            documentId.value,
            text
        )
    }
}