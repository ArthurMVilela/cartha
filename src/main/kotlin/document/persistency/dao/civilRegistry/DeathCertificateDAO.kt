package document.persistency.dao.civilRegistry

import document.civilRegistry.DeathCertificate
import document.persistency.dao.DAO
import org.jetbrains.exposed.sql.Op

class DeathCertificateDAO:DAO<DeathCertificate, String> {
    override fun insert(obj: DeathCertificate) {
        TODO("Not yet implemented")
    }

    override fun select(id: String): DeathCertificate? {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>): List<DeathCertificate> {
        TODO("Not yet implemented")
    }

    override fun selectAll(): List<DeathCertificate> {
        TODO("Not yet implemented")
    }

    override fun update(oldId: String, new: DeathCertificate) {
        TODO("Not yet implemented")
    }

    override fun delete(id: String) {
        TODO("Not yet implemented")
    }

    override fun deleteWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }
}