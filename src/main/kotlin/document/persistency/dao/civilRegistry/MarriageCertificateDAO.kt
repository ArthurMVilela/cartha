package document.persistency.dao.civilRegistry

import document.civilRegistry.MarriageCertificate
import document.civilRegistry.Spouse
import document.persistency.dao.CompanionDAO
import document.persistency.dao.DAO
import document.persistency.dao.PhysicalPersonDAO
import document.persistency.tables.civilRegistry.MarriageCertificateTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.emptySized
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception
class MarriageCertificateDAO(id:EntityID<String>):Entity<String>(id), DAO<MarriageCertificate> {
    companion object : CompanionDAO<MarriageCertificate, MarriageCertificateDAO, String, IdTable<String>>(MarriageCertificateTable){
        override fun insert(obj: MarriageCertificate): MarriageCertificateDAO {
            var r:MarriageCertificateDAO? = null
            transaction {
                try {
                    CivilRegistryDocumentDAO.insert(obj)
                    val first = SpouseDAO.insert(obj.firstSpouse)
                    val second = SpouseDAO.insert(obj.secondSpouse)
                    r = MarriageCertificateDAO.new(obj.id!!) {
                        firstSpouseId = SpouseDAO.select(obj.firstSpouse.id!!)!!.id
                        secondSpouseId = SpouseDAO.select(obj.secondSpouse.id!!)!!.id
                        dateOfRegistry = obj.dateOfRegistry
                        matrimonialRegime = obj.matrimonialRegime
                    }

                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
            return r!!
        }

        override fun select(id: String): MarriageCertificateDAO? {
            var r: MarriageCertificateDAO? = null
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

        override fun selectMany(condition: Op<Boolean>): SizedIterable<MarriageCertificateDAO> {
            var r:SizedIterable<MarriageCertificateDAO> = emptySized()
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

        override fun selectAll(): SizedIterable<MarriageCertificateDAO> {
            var r:SizedIterable<MarriageCertificateDAO> = emptySized()
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

        override fun update(obj: MarriageCertificate) {
            transaction {
                try {
                    CivilRegistryDocumentDAO.update(obj)
                    val found = MarriageCertificateDAO.findById(obj.id!!)!!
                    found.firstSpouseId = SpouseDAO.select(obj.firstSpouse.id!!)!!.id
                    found.secondSpouseId = SpouseDAO.select(obj.secondSpouse.id!!)!!.id
                    found.dateOfRegistry = obj.dateOfRegistry
                    found.matrimonialRegime = obj.matrimonialRegime

                    SpouseDAO.update(obj.firstSpouse)
                    SpouseDAO.update(obj.secondSpouse)
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
    val firstSpouse by SpouseDAO referencedOn MarriageCertificateTable.firstSpouseId
    val secondSpouse by SpouseDAO referencedOn MarriageCertificateTable.secondSpouseId
    var firstSpouseId by MarriageCertificateTable.firstSpouseId
    var secondSpouseId by MarriageCertificateTable.secondSpouseId
    var dateOfRegistry by MarriageCertificateTable.dateOfRegistry
    var matrimonialRegime by MarriageCertificateTable.matrimonialRegime

    override fun toType(): MarriageCertificate? {
        val parent = CivilRegistryDocumentDAO.select(id.value)!!.toType()!!
        var first:Spouse? = null
        var second:Spouse? = null
        transaction {
            first = firstSpouse.toType()!!
            second = secondSpouse.toType()!!
        }
        return MarriageCertificate(
            parent.id, parent.status, parent.officialId, parent.notaryId,
            parent.registration, parent.registering,
            first!!, second!!,
            dateOfRegistry, matrimonialRegime
        )
    }
}

