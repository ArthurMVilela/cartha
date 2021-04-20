package document.persistence.dao.civilRegistry

import document.DocumentStatus
import document.civilRegistry.CivilRegistryDocument
import document.civilRegistry.Registering
import document.persistence.dao.*
import document.persistence.tables.civilRegistry.CivilRegistryDocumentTable
import document.persistence.tables.civilRegistry.RegisteringTable
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

class CivilRegistryDocumentDAO(id: EntityID<String>):Entity<String>(id), DAO<CivilRegistryDocument> {
    companion object : CompanionDAO<CivilRegistryDocument, CivilRegistryDocumentDAO, String, IdTable<String>>(CivilRegistryDocumentTable) {
        override fun insert(obj: CivilRegistryDocument): CivilRegistryDocumentDAO {
            var r:CivilRegistryDocumentDAO? = null
            transaction {
                try {
                    DocumentDAO.insert(obj)
                    r = new(obj.id!!) {
                        registration = obj.registration
                    }
                    obj.registering.forEach {
                        RegisteringDAO.insert(it)
                    }
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
            return r!!
        }

        override fun select(id: String): CivilRegistryDocumentDAO? {
            var r: CivilRegistryDocumentDAO? = null
            transaction {
                try {
                    r = findById(id)
                } catch (e: Exception){
                    rollback()
                    throw e
                }
            }
            return r
        }

        override fun selectMany(condition: Op<Boolean>): SizedIterable<CivilRegistryDocumentDAO> {
            var r:SizedIterable<CivilRegistryDocumentDAO> = emptySized()
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

        override fun selectAll(): SizedIterable<CivilRegistryDocumentDAO> {
            var r:SizedIterable<CivilRegistryDocumentDAO> = emptySized()
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

        override fun update(obj: CivilRegistryDocument) {
            transaction {
                try {
                    DocumentDAO.update(obj)
                    val found = findById(obj.id!!)!!
                    found.registration = obj.registration

                    val foundRegistering = found.registering.toList().map { it.toType()!! }
                    found.registering.forEach {
                        it.delete()
                    }
                    foundRegistering.forEach {
                        RegisteringDAO.insert(it)
                    }
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
        }

        override fun remove(id: String) {
            transaction {
                try {
                    RegisteringDAO.removeWhere(Op.build { RegisteringTable.documentID eq id })
                    findById(id)?.delete()
                    DocumentDAO.remove(id)
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
                        RegisteringDAO.removeWhere(Op.build { RegisteringTable.documentID eq it.id })
                        it.delete()
                        DocumentDAO.remove(it.id.value)
                    }

                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
        }
    }

    var registration by CivilRegistryDocumentTable.registration
    val registering by RegisteringDAO referrersOn RegisteringTable.documentID

    override fun toType(): CivilRegistryDocument? {
        val parent = DocumentDAO.select(id.value)!!.toType()
        var list = listOf<Registering>()
        transaction {
            list = registering.map {
                it.toType()!!
            }
        }
        return object : CivilRegistryDocument() {
            override val id: String? = parent!!.id
            override val status: DocumentStatus = parent!!.status
            override val officialId: String = parent!!.officialId
            override val notaryId: String = parent!!.notaryId
            override val registration: String = this@CivilRegistryDocumentDAO.registration
            override val registering: List<Registering> = list
        }
    }
}