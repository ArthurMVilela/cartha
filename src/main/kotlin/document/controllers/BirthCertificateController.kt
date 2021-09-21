package document.controllers

import document.civilRegistry.birth.BirthCertificate
import document.persistence.dao.civilRegistry.birth.BirthCertificateDAO

class BirthCertificateController {
    private val birthCertificateDAO = BirthCertificateDAO()

    fun createBirthCertificate(bc: BirthCertificate): BirthCertificate {
        return birthCertificateDAO.insert(bc)
    }
}