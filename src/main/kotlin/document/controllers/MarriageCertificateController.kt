package document.controllers

import document.civilRegistry.marriage.MarriageCertificate
import document.persistence.dao.civilRegistry.marriage.MarriageCertificateDAO
import java.util.*

class MarriageCertificateController {
    private val marriageCertificateDAO = MarriageCertificateDAO()

    fun createMarriageCertificate(mc: MarriageCertificate):MarriageCertificate {
        return marriageCertificateDAO.insert(mc)
    }

    fun getMarriageCertificate(id: UUID): MarriageCertificate? {
        return marriageCertificateDAO.select(id)
    }
}