package document.persistence.dao.civilRegistry

import document.civilRegistry.DeathCertificate
import persistence.CompanionDAO
import persistence.DAO
import document.persistence.dao.PhysicalPersonDAO
import document.persistence.tables.civilRegistry.DeathCertificateTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.emptySized
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

class DeathCertificateDAO(id: EntityID<String>):Entity<String>(id), DAO<DeathCertificate> {
    companion object : CompanionDAO<DeathCertificate, DeathCertificateDAO, String, IdTable<String>>(DeathCertificateTable){
        override fun insert(obj: DeathCertificate): DeathCertificateDAO {
            var r:DeathCertificateDAO? = null
            transaction {
                try {
                    CivilRegistryDocumentDAO.insert(obj)
                    val affiliation = AffiliationDAO.insert(obj.affiliation)
                    r = new(obj.id!!) {
                        personId = PhysicalPersonDAO.select(obj.personId)!!.id
                        sex = obj.sex
                        color = obj.color
                        civilStatus = obj.civilStatus
                        age = obj.age

                        birthPlace = obj.birthPlace
                        documentOfIdentity = obj.documentOfIdentity
                        affiliationId = affiliation.id
                        residency = obj.residency

                        dateTimeOfDeath = obj.dateTimeOfDeath
                        placeOfDeath = obj.placeOfDeath
                        causeOfDeath = obj.causeOfDeath
                        burialOrCremationLocation = obj.burialOrCremationLocation
                        documentDeclaringDeath = obj.documentDeclaringDeath
                    }

                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
            return r!!
        }

        override fun select(id: String): DeathCertificateDAO? {
            var r: DeathCertificateDAO? = null
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

        override fun selectMany(condition: Op<Boolean>): SizedIterable<DeathCertificateDAO> {
            var r:SizedIterable<DeathCertificateDAO> = emptySized()
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

        override fun selectAll(): SizedIterable<DeathCertificateDAO> {
            var r:SizedIterable<DeathCertificateDAO> = emptySized()
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

        override fun update(obj: DeathCertificate) {
            transaction {
                try {
                    CivilRegistryDocumentDAO.update(obj)
                    val found = findById(obj.id!!)!!
                    found.personId = PhysicalPersonDAO.select(obj.personId)!!.id
                    found.sex = obj.sex
                    found.color = obj.color
                    found.civilStatus = obj.civilStatus
                    found.age = obj.age

                    found.birthPlace = obj.birthPlace
                    found.documentOfIdentity = obj.documentOfIdentity
                    found.residency = obj.residency

                    found.dateTimeOfDeath = obj.dateTimeOfDeath
                    found.placeOfDeath = obj.placeOfDeath
                    found.causeOfDeath = obj.causeOfDeath
                    found.burialOrCremationLocation = obj.burialOrCremationLocation
                    found.documentDeclaringDeath = obj.documentDeclaringDeath

                    val foundAffiliation = found.affiliation.toType()!!
                    AffiliationDAO.update(foundAffiliation)
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
        }

        override fun remove(id: String) {
            transaction {
                try {
                    val found = findById(id)
                    found!!.delete()
                    AffiliationDAO.remove(found!!.affiliation.id.value)
                    CivilRegistryDocumentDAO.remove(id)
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
                        AffiliationDAO.remove(it.affiliation.id.value)
                        CivilRegistryDocumentDAO.remove(it.id.value)
                    }

                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
        }
    }

    var personId by DeathCertificateTable.personId
    var sex by DeathCertificateTable.sex
    var color by DeathCertificateTable.color
    var civilStatus by DeathCertificateTable.civilStatus
    var age by DeathCertificateTable.age

    var birthPlace by DeathCertificateTable.birthPlace
    var documentOfIdentity by DeathCertificateTable.documentOfIdentity
    var affiliationId by DeathCertificateTable.affiliationId
    val affiliation by AffiliationDAO referencedOn DeathCertificateTable.affiliationId
    var residency by DeathCertificateTable.residency

    var dateTimeOfDeath by DeathCertificateTable.dateTimeOfDeath
    var placeOfDeath by DeathCertificateTable.placeOfDeath
    var causeOfDeath by DeathCertificateTable.causeOfDeath
    var burialOrCremationLocation by DeathCertificateTable.burialOrCremationLocation
    var documentDeclaringDeath by DeathCertificateTable.documentDeclaringDeath

    override fun toType(): DeathCertificate? {
        val parent = CivilRegistryDocumentDAO.select(id.value)!!.toType()!!
        val affiliation = AffiliationDAO.select(affiliationId.value)!!.toType()!!
        return DeathCertificate(
            parent.id, parent.status, parent.officialId, parent.notaryId,
            parent.registration, parent.registering,
            personId.value, sex, color, civilStatus, age,
            birthPlace, documentOfIdentity, affiliation, residency,
            dateTimeOfDeath, placeOfDeath, causeOfDeath, burialOrCremationLocation, documentDeclaringDeath
        )
    }
}