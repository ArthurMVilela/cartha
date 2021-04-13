package document.persistency.dao.civilRegistry

import document.persistency.dao.DAO
import org.jetbrains.exposed.sql.Op

class MarriageCertificateDAO:DAO<MarriageCertificateDAO, String> {
    override fun insert(obj: MarriageCertificateDAO) {
        TODO("Not yet implemented")
    }

    override fun select(id: String): MarriageCertificateDAO? {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>): List<MarriageCertificateDAO> {
        TODO("Not yet implemented")
    }

    override fun selectAll(): List<MarriageCertificateDAO> {
        TODO("Not yet implemented")
    }

    override fun update(oldId: String, new: MarriageCertificateDAO) {
        TODO("Not yet implemented")
    }

    override fun delete(id: String) {
        TODO("Not yet implemented")
    }

    override fun deleteWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }
}