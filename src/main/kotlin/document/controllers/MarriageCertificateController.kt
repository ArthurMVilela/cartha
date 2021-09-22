package document.controllers

import document.civilRegistry.marriage.MarriageCertificate
import document.persistence.dao.civilRegistry.marriage.MarriageCertificateDAO

class MarriageCertificateController {
    private val marriageCertificateDAO = MarriageCertificateDAO()

    fun createMarriageCertificate(mc: MarriageCertificate):MarriageCertificate {
        return marriageCertificateDAO.insert(mc)
    }
}