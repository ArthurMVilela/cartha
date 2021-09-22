package document.persistence.dao.civilRegistry.marriage

import document.civilRegistry.Registering
import document.civilRegistry.marriage.MarriageCertificate
import document.persistence.tables.DocumentTable
import document.persistence.tables.civilRegistry.CivilRegistryDocumentTable
import document.persistence.tables.civilRegistry.marriage.MarriageCertificateTable
import document.persistence.tables.civilRegistry.marriage.SpouseTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import persistence.DAO
import persistence.ResultSet
import java.util.*

class MarriageCertificateDAO:DAO<MarriageCertificate, UUID> {
    private val spouseDAO = SpouseDAO()

    val table: Join = MarriageCertificateTable
        .join(
            CivilRegistryDocumentTable,
            JoinType.INNER,
            additionalConstraint = { MarriageCertificateTable.id eq CivilRegistryDocumentTable.id}
        )
        .join(
            DocumentTable,
            JoinType.INNER,
            additionalConstraint = { DocumentTable.id eq MarriageCertificateTable.id}
        )

    override fun insert(obj: MarriageCertificate): MarriageCertificate {
        var inserted:MarriageCertificate? = null

        transaction {
            try {
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
                MarriageCertificateTable.insert {
                    it[id] = insertedId
                    it[dateOfRegistry] = obj.dateOfRegistry
                    it[matrimonialRegime] = obj.matrimonialRegime
                }
                obj.spouses.forEach {
                    spouseDAO.insert(it)
                }

                inserted = toType(table.select(Op.build { MarriageCertificateTable.id eq insertedId }).first())
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return inserted!!
    }

    override fun select(id: UUID): MarriageCertificate? {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>, page: Int, pageLength: Int): ResultSet<MarriageCertificate> {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>): List<MarriageCertificate> {
        TODO("Not yet implemented")
    }

    override fun selectAll(page: Int, pageLength: Int): ResultSet<MarriageCertificate> {
        TODO("Not yet implemented")
    }

    override fun update(obj: MarriageCertificate) {
        TODO("Not yet implemented")
    }

    override fun remove(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun removeWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }

    override fun toType(row: ResultRow): MarriageCertificate {
        val id = row[MarriageCertificateTable.id].value
        val status = row[DocumentTable.status]
        val officialId = row[DocumentTable.officialId].value
        val notaryId = row[DocumentTable.notaryId].value
        val hash = row[DocumentTable.hash]
        val registrationNumber = row[CivilRegistryDocumentTable.registrationNumber]
        val registering = mutableListOf<Registering>()
        val spouses = spouseDAO.selectMany(Op.build { SpouseTable.marriageCertificateId eq id })
        val dateOfRegistry = row[MarriageCertificateTable.dateOfRegistry]
        val matrimonialRegime = row[MarriageCertificateTable.matrimonialRegime]

        return MarriageCertificate(
            id, status, officialId, notaryId, hash, registrationNumber,
            registering,spouses, dateOfRegistry, matrimonialRegime
        )
    }
}