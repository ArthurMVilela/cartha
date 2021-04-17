package document.persistency.dao.civilRegistry

import document.civilRegistry.BirthCertificate
import document.persistency.dao.CompanionDAO
import document.persistency.dao.DAO
import document.persistency.tables.civilRegistry.BirthCertificateTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SizedIterable

class BirthCertificateDAO(id: EntityID<String>):Entity<String>(id), DAO<BirthCertificate> {
    companion object : CompanionDAO<BirthCertificate, BirthCertificateDAO, String, IdTable<String>>(BirthCertificateTable) {
        override fun insert(obj: BirthCertificate): BirthCertificateDAO {
            TODO("Not yet implemented")
        }

        override fun select(id: String): BirthCertificateDAO? {
            TODO("Not yet implemented")
        }

        override fun selectMany(condition: Op<Boolean>): SizedIterable<BirthCertificateDAO> {
            TODO("Not yet implemented")
        }

        override fun selectAll(): SizedIterable<BirthCertificateDAO> {
            TODO("Not yet implemented")
        }

        override fun update(obj: BirthCertificate) {
            TODO("Not yet implemented")
        }

        override fun remove(id: String) {
            TODO("Not yet implemented")
        }

        override fun removeWhere(condition: Op<Boolean>) {
            TODO("Not yet implemented")
        }
    }

    override fun toType(): BirthCertificate? {
        TODO("Not yet implemented")
    }
}