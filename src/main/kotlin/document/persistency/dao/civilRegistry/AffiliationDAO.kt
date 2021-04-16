package document.persistency.dao.civilRegistry

import document.civilRegistry.Affiliation
import document.persistency.dao.CompanionDAO
import document.persistency.dao.DAO
import document.persistency.dao.NotaryDAO
import document.persistency.dao.PhysicalPersonDAO
import document.persistency.tables.civilRegistry.AffiliationTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.emptySized
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

class AffiliationDAO(id:EntityID<String>): Entity<String>(id), DAO<Affiliation> {
    companion object : CompanionDAO<Affiliation, AffiliationDAO, String, IdTable<String>>(AffiliationTable) {
        override fun insert(obj: Affiliation): AffiliationDAO {
            var r: AffiliationDAO? = null
            transaction {
                try {
                    r = new(obj.id!!) {
                        personId = PhysicalPersonDAO.select(obj.personId)!!.id
                        name = obj.name
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

        override fun select(id: String): AffiliationDAO? {
            var r:AffiliationDAO? = null
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

        override fun selectMany(condition: Op<Boolean>): SizedIterable<AffiliationDAO> {
            var r:SizedIterable<AffiliationDAO> = emptySized()
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

        override fun selectAll(): SizedIterable<AffiliationDAO> {
            var r:SizedIterable<AffiliationDAO> = emptySized()
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

        override fun update(obj: Affiliation) {
            transaction {
                try {
                    val found = findById(obj.id!!)!!
                    found.personId = PhysicalPersonDAO.select(obj.personId)!!.id
                    found.name = obj.name
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

    var personId by AffiliationTable.personId
    var name by AffiliationTable.name
    var uf by AffiliationTable.uf
    var municipality by AffiliationTable.municipality

    override fun toType(): Affiliation? {
        return Affiliation(
            id.value,
            personId.value,
            name,
            uf,
            municipality
        )
    }
}