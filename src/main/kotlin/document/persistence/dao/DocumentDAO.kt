package document.persistence.dao

import document.Document
import document.DocumentStatus
import document.persistence.tables.DocumentTable
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

class DocumentDAO(id:EntityID<String>):Entity<String>(id), DAO<Document> {
    companion object : CompanionDAO<Document, DocumentDAO, String, IdTable<String>>(DocumentTable) {
        override fun insert(obj: Document): DocumentDAO {
            var r:DocumentDAO? = null
            transaction {
                try {
                    r = new(obj.id!!) {
                        status = obj.status
                        officialId = OfficialDAO.select(obj.officialId)!!.id
                        notaryId = NotaryDAO.select(obj.notaryId)!!.id
                    }
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
            return r!!
        }

        override fun select(id: String): DocumentDAO? {
            var r:DocumentDAO? = null
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

        override fun selectMany(condition: Op<Boolean>): SizedIterable<DocumentDAO> {
            var r:SizedIterable<DocumentDAO> = emptySized()
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

        override fun selectAll(): SizedIterable<DocumentDAO> {
            var r:SizedIterable<DocumentDAO> = emptySized()
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

        override fun update(obj: Document) {
            transaction {
                try {
                    val found = findById(obj.id!!)!!
                    found.status = obj.status
                    found.officialId = OfficialDAO.select(obj.officialId)!!.id
                    found.notaryId = NotaryDAO.select(obj.notaryId)!!.id
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

    var status by DocumentTable.status
    var officialId by DocumentTable.officialId
    var notaryId by DocumentTable.notaryId

    override fun toType(): Document? {
        return object : Document() {
            override val id: String? = this@DocumentDAO.id.value
            override val status: DocumentStatus = this@DocumentDAO.status
            override val officialId: String = this@DocumentDAO.officialId.value
            override val notaryId: String = this@DocumentDAO.notaryId.value
        }
    }
}