package document.persistency.dao.civilRegistry

import document.civilRegistry.BirthCertificate
import document.persistency.dao.CompanionDAO
import document.persistency.dao.DAO
import document.persistency.dao.PhysicalPersonDAO
import document.persistency.tables.civilRegistry.BirthCertificateTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.emptySized
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BirthCertificateDAO(id: EntityID<String>):Entity<String>(id), DAO<BirthCertificate> {
    companion object : CompanionDAO<BirthCertificate, BirthCertificateDAO, String, IdTable<String>>(BirthCertificateTable) {
        override fun insert(obj: BirthCertificate): BirthCertificateDAO {
            var r:BirthCertificateDAO? = null
            transaction {
                try {
                    CivilRegistryDocumentDAO.insert(obj)
                    r = new(obj.id!!) {
                        dateTimeOfBirth = LocalDateTime.of(obj.dateOfBirth, obj.timeOfBirth)
                        municipalityOfBirth = obj.municipalityOfBirth
                        ufOfBirth = obj.ufOfBirth
                        municipalityOfRegistry = obj.municipalityOfRegistry
                        ufOfRegistry = obj.ufOfRegistry
                        twin = obj.twin
                        dateOfRegistry = obj.dateOfRegistry
                        DNNumber = obj.DNNumber
                        personId = PhysicalPersonDAO.select(obj.personId!!)!!.id
                        cpf = obj.cpf
                        name = obj.name
                        sex = obj.sex
                    }
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
            return r!!
        }

        override fun select(id: String): BirthCertificateDAO? {
            var r: BirthCertificateDAO? = null
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

        override fun selectMany(condition: Op<Boolean>): SizedIterable<BirthCertificateDAO> {
            var r:SizedIterable<BirthCertificateDAO> = emptySized()
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

        override fun selectAll(): SizedIterable<BirthCertificateDAO> {
            var r:SizedIterable<BirthCertificateDAO> = emptySized()
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

        override fun update(obj: BirthCertificate) {
            transaction {
                try {
                    CivilRegistryDocumentDAO.update(obj)
                    val found = findById(obj.id!!)!!
                    found.dateTimeOfBirth = LocalDateTime.of(obj.dateOfBirth, obj.timeOfBirth)
                    found.municipalityOfBirth = obj.municipalityOfBirth
                    found.ufOfBirth = obj.ufOfBirth
                    found.municipalityOfRegistry = obj.municipalityOfRegistry
                    found.ufOfRegistry = obj.ufOfRegistry
                    found.twin = obj.twin
                    found.dateOfRegistry = obj.dateOfRegistry
                    found.DNNumber = obj.DNNumber
                    found.personId = PhysicalPersonDAO.select(obj.personId!!)!!.id
                    found.cpf = obj.cpf
                    found.name = obj.name
                    found.sex = obj.sex
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
                        CivilRegistryDocumentDAO.remove(it.id.value)
                    }

                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
        }
    }

    var dateTimeOfBirth by BirthCertificateTable.dateTimeOfBirth
    var municipalityOfBirth by BirthCertificateTable.municipalityOfBirth
    var ufOfBirth by BirthCertificateTable.ufOfBirth
    var municipalityOfRegistry by BirthCertificateTable.municipalityOfRegistry
    var ufOfRegistry by BirthCertificateTable.ufOfRegistry
    var twin by BirthCertificateTable.twin
    var dateOfRegistry by BirthCertificateTable.dateOfRegistry
    var DNNumber by BirthCertificateTable.DNNumber
    var personId by BirthCertificateTable.personId
    var cpf by BirthCertificateTable.cpf
    var name by BirthCertificateTable.name
    var sex by BirthCertificateTable.sex

    override fun toType(): BirthCertificate? {
        val parent = CivilRegistryDocumentDAO.select(id.value)!!.toType()!!
        return BirthCertificate(
            parent.id, parent.status, parent.officialId, parent.notaryId, parent.registration, parent.registering,
            LocalTime.of(dateTimeOfBirth.hour, dateTimeOfBirth.minute, dateTimeOfBirth.second),
            municipalityOfBirth, ufOfBirth,
            municipalityOfRegistry, ufOfRegistry,
            listOf(),
            listOf(),
            false,
            listOf(),
            dateOfRegistry,
            DNNumber,
            personId.value,
            cpf, name,
            LocalDate.of(dateTimeOfBirth.year, dateTimeOfBirth.month, dateTimeOfBirth.dayOfMonth),
            sex
        )
    }
}