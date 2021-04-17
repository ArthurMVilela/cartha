package document.persistence.dao.civilRegistry

import document.civilRegistry.Grandparent
import persistence.CompanionDAO
import persistence.DAO
import document.persistence.dao.PersonDAO
import document.persistence.tables.civilRegistry.GrandparentTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.emptySized
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

class GrandparentDAO(id:EntityID<String>):Entity<String>(id), DAO<Grandparent> {
    companion object : CompanionDAO<Grandparent, GrandparentDAO, String, IdTable<String>>(GrandparentTable) {
        override fun insert(obj: Grandparent): GrandparentDAO {
            var r: GrandparentDAO? = null
            transaction {
                try {
                    r = new(obj.id!!) {
                        documentId = CivilRegistryDocumentDAO.select(obj.id!!)!!.id
                        personId = PersonDAO.select(obj.id!!)!!.id
                        name = obj.name
                        type = obj.type
                        uf = obj.uf
                        municipality = obj.municipality
                    }
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
            return r!!
        }

        override fun select(id: String): GrandparentDAO? {
            var r:GrandparentDAO? = null
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

        override fun selectMany(condition: Op<Boolean>): SizedIterable<GrandparentDAO> {
            var r:SizedIterable<GrandparentDAO> = emptySized()
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

        override fun selectAll(): SizedIterable<GrandparentDAO> {
            var r:SizedIterable<GrandparentDAO> = emptySized()
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

        override fun update(obj: Grandparent) {
            transaction {
                try {
                    val found = findById(obj.id!!)!!
                    found.documentId = CivilRegistryDocumentDAO.select(obj.id!!)!!.id
                    found.personId = PersonDAO.select(obj.id!!)!!.id
                    found.name = obj.name
                    found.type = obj.type
                    found.uf = obj.uf
                    found.municipality = obj.municipality
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
    var documentId by GrandparentTable.documentId
    var personId by GrandparentTable.personId
    var name by GrandparentTable.name
    var type by GrandparentTable.type
    var uf by GrandparentTable.uf
    var municipality by GrandparentTable.municipality

    override fun toType(): Grandparent? {
        return Grandparent(
            id.value, documentId.value, personId.value, name, type, uf, municipality
        )
    }
}