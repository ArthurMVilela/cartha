package document.persistence.dao.civilRegistry.birth

import document.civilRegistry.Registering
import document.civilRegistry.birth.BirthCertificate
import document.civilRegistry.birth.Grandparent
import document.civilRegistry.birth.Twin
import document.persistence.dao.address.MunicipalityDAO
import document.persistence.dao.civilRegistry.AffiliationDAO
import document.persistence.tables.DocumentTable
import document.persistence.tables.civilRegistry.AffiliationTable
import document.persistence.tables.civilRegistry.CivilRegistryDocumentTable
import document.persistence.tables.civilRegistry.birth.BirthCertificateTable
import document.persistence.tables.civilRegistry.birth.GrandparentTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import persistence.DAO
import persistence.ResultSet
import java.util.*

class BirthCertificateDAO:DAO<BirthCertificate, UUID> {
    private val affiliationDAO = AffiliationDAO()
    private val municipalityDAO = MunicipalityDAO()
    private val grandparentDAO = GrandparentDAO()

    var table: Join

    init {
        table = BirthCertificateTable
            .join(
                CivilRegistryDocumentTable,
                JoinType.INNER,
                additionalConstraint = {BirthCertificateTable.id eq CivilRegistryDocumentTable.id}
            )
            .join(
                DocumentTable,
                JoinType.INNER,
                additionalConstraint = {DocumentTable.id eq BirthCertificateTable.id}
            )
    }

    override fun insert(obj: BirthCertificate): BirthCertificate {
        var inserted: BirthCertificate? = null

        transaction {
            try {
                val municipalityOfBirth = municipalityDAO.insert(obj.municipalityOfBirth)
                val municipalityOfRegistry = municipalityDAO.insert(obj.municipalityOfRegistry)
                val insertedId = DocumentTable.insertAndGetId {
                    it[id] = obj.id
                    it[status] = obj.status
                    it[officialId] = obj.officialId
                    it[notaryId] = obj.notaryId
                    it[hash] = obj.hash!!
                }
                CivilRegistryDocumentTable.insert {
                    it[id] = insertedId
                    it[registrationNumber] = obj.registrationNumber!!
                }
                BirthCertificateTable.insert {
                    it[id] = obj.id
                    it[personId] = null
                    it[name] = obj.name
                    it[sex] = obj.sex
                    it[municipalityOfBirthId] = municipalityOfBirth.id
                    it[municipalityOfRegistryId] = municipalityOfRegistry.id
                    it[placeOfBirth] = obj.placeOfBirth
                    it[dateTimeOfBirth] = obj.dateTimeOfBirth
                    it[dateOfRegistry] = obj.dateOfRegistry
                    it[dnNumber] = obj.dnNumber
                }

                obj.affiliation.forEach {
                    affiliationDAO.insert(it)
                }
                obj.grandparents.forEach {
                    grandparentDAO.insert(it)
                }

                inserted = toType(table.select(Op.build { CivilRegistryDocumentTable.id eq insertedId }).first())
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return inserted!!
    }

    override fun select(id: UUID): BirthCertificate? {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>, page: Int, pageLength: Int): ResultSet<BirthCertificate> {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>): List<BirthCertificate> {
        TODO("Not yet implemented")
    }

    override fun selectAll(page: Int, pageLength: Int): ResultSet<BirthCertificate> {
        TODO("Not yet implemented")
    }

    override fun update(obj: BirthCertificate) {
        TODO("Not yet implemented")
    }

    override fun remove(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun removeWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }

    override fun toType(row: ResultRow): BirthCertificate {
        val id = row[BirthCertificateTable.id].value
        val status = row[DocumentTable.status]
        val officialId = row[DocumentTable.officialId].value
        val notaryId = row[DocumentTable.notaryId].value
        val hash = row[DocumentTable.hash]
        val registrationNumber = row[CivilRegistryDocumentTable.registrationNumber]
        val registering = mutableListOf<Registering>()

        val personId = row[BirthCertificateTable.personId]?.value
        val name = row[BirthCertificateTable.name]
        val sex = row[BirthCertificateTable.sex]
        val municipalityOfBirth = municipalityDAO.select(row[BirthCertificateTable.municipalityOfBirthId].value)!!
        val municipalityOfRegistry = municipalityDAO.select(row[BirthCertificateTable.municipalityOfRegistryId].value)!!
        val placeOfBirth = row[BirthCertificateTable.placeOfBirth]
        val affiliation = affiliationDAO.selectMany(Op.build { AffiliationTable.documentId eq id })
        val grandparents = grandparentDAO.selectMany(Op.build { GrandparentTable.birthCertificateId eq id })
        val dateTimeOfBirth = row[BirthCertificateTable.dateTimeOfBirth]
        val dateOfRegistry = row[BirthCertificateTable.dateOfRegistry]
        val twins = mutableSetOf<Twin>()
        val dnnNumber = row[BirthCertificateTable.dnNumber]

        return BirthCertificate(
            id, status, officialId, notaryId, hash, registrationNumber, registering,
            personId, name, sex, municipalityOfBirth, municipalityOfRegistry, placeOfBirth, affiliation, grandparents,
            dateTimeOfBirth,dateOfRegistry, twins, dnnNumber
        )
    }
}