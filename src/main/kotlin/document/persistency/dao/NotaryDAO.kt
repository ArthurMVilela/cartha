package document.persistency.dao

import document.Notary
import document.persistency.tables.NotaryTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.emptySized
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

class NotaryDAO(id:EntityID<String>):Entity<String>(id), DAO<Notary> {
    companion object : CompanionDAO<Notary, NotaryDAO, String, IdTable<String>>(NotaryTable) {
        override fun insert(obj: Notary): NotaryDAO {
            var r:NotaryDAO? = null
            transaction {
                try {
                    r = new(obj.id!!) {
                        name = obj.name
                        cnpj = obj.cnpj
                        cns = obj.cns
                    }
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
            return r!!
        }

        override fun select(id: String): NotaryDAO? {
            var r:NotaryDAO? = null
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

        override fun selectMany(condition: Op<Boolean>): SizedIterable<NotaryDAO> {
            var r:SizedIterable<NotaryDAO> = emptySized()
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

        override fun selectAll(): SizedIterable<NotaryDAO> {
            var r:SizedIterable<NotaryDAO> = emptySized()
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

        override fun update(obj: Notary) {
            transaction {
                try {
                    val found = findById(obj.id!!)!!
                    found.name = obj.name
                    found.cnpj = obj.cnpj
                    found.cns = obj.cns
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

    var name by NotaryTable.name
    var cnpj by NotaryTable.cnpj
    var cns by NotaryTable.cns

    override fun toType(): Notary? {
        return Notary(
            id.value,
            name,
            cnpj,
            cns
        )
    }
}