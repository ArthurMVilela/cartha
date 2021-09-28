package document.controllers

import document.civilRegistry.birth.BirthCertificate
import document.persistence.dao.civilRegistry.AffiliationDAO
import document.persistence.dao.civilRegistry.birth.BirthCertificateDAO
import document.persistence.tables.DocumentTable
import document.persistence.tables.civilRegistry.AffiliationTable
import document.persistence.tables.civilRegistry.birth.BirthCertificateTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import persistence.ResultSet
import java.util.*

class BirthCertificateController {
    private val birthCertificateDAO = BirthCertificateDAO()
    private val affiliationDAO = AffiliationDAO()

    fun createBirthCertificate(bc: BirthCertificate): BirthCertificate {
        return birthCertificateDAO.insert(bc)
    }

    fun getBirthCertificate(id: UUID): BirthCertificate? {
        return birthCertificateDAO.select(id)
    }

    fun getBirthCertificate(cpf: String): BirthCertificate? {
        return birthCertificateDAO.selectMany(Op.build { BirthCertificateTable.cpf eq cpf }).firstOrNull()
    }

    fun getBirthCertificatesByOfficial(officialId: UUID, page: Int): ResultSet<BirthCertificate> {
        return birthCertificateDAO.selectMany(Op.build { DocumentTable.officialId eq officialId }, page)
    }

    fun getBirthCertificatesWithAffiliation(cpf: String): List<BirthCertificate> {
        val affiliations = affiliationDAO.selectMany(Op.build { AffiliationTable.cpf eq cpf })
        val ids = affiliations.map {
            it.documentId
        }

        return birthCertificateDAO.selectMany(Op.build { BirthCertificateTable.id inList ids})
    }

    fun getBirthCertificatesByNotary(notaryId: UUID, page: Int): ResultSet<BirthCertificate> {
        return birthCertificateDAO.selectMany(Op.build { DocumentTable.notaryId eq notaryId }, page)
    }
}