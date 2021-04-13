package document.persistency.dao.civilRegistry

import document.civilRegistry.BirthCertificate
import document.persistency.dao.DAO
import org.jetbrains.exposed.sql.Op

class BirthCertificateDAO: DAO<BirthCertificate, String> {
    override fun insert(obj: BirthCertificate) {
        TODO("Not yet implemented")
    }

    override fun select(id: String): BirthCertificate? {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>): List<BirthCertificate> {
        TODO("Not yet implemented")
    }

    override fun selectAll(): List<BirthCertificate> {
        TODO("Not yet implemented")
    }

    override fun update(oldId: String, new: BirthCertificate) {
        TODO("Not yet implemented")
    }

    override fun delete(id: String) {
        TODO("Not yet implemented")
    }

    override fun deleteWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }
}