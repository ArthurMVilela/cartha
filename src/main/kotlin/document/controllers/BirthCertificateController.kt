package document.controllers

import document.civilRegistry.birth.BirthCertificate
import document.persistence.dao.civilRegistry.birth.BirthCertificateDAO
import java.util.*

class BirthCertificateController {
    private val birthCertificateDAO = BirthCertificateDAO()

    fun createBirthCertificate(bc: BirthCertificate): BirthCertificate {
        return birthCertificateDAO.insert(bc)
    }

    fun getBirthCertificate(id: UUID): BirthCertificate? {
        return birthCertificateDAO.select(id)
    }
}