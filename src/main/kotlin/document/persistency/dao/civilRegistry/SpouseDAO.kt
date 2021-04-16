package document.persistency.dao.civilRegistry

import document.civilRegistry.Affiliation
import document.civilRegistry.Spouse
import document.persistency.dao.CompanionDAO
import document.persistency.dao.DAO
import document.persistency.dao.PhysicalPersonDAO
import document.persistency.tables.civilRegistry.SpouseAffiliationTable
import document.persistency.tables.civilRegistry.SpouseTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

class SpouseDAO(id:EntityID<String>):Entity<String>(id), DAO<Spouse>{
    companion object : CompanionDAO<Spouse, SpouseDAO, String, IdTable<String>>(SpouseTable) {
        override fun insert(obj: Spouse): SpouseDAO {
            var r:SpouseDAO? = null
            transaction {
                try {
                    r = new(obj.id!!) {
                        singleName = obj.singleName
                        marriedName = obj.marriedName
                        personId = PhysicalPersonDAO.select(obj.personId)!!.id
                        birthday = obj.birthday
                        nationality = obj.nationality
                    }
                    obj.affiliations.forEach {
                        val inserted = AffiliationDAO.insert(it)
                        SpouseAffiliationTable.insert {
                            it[spouseId] = obj.id!!
                            it[affiliationId] = inserted.id
                        }
                    }
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
            return r!!
        }

        override fun select(id: String): SpouseDAO? {
            var r: SpouseDAO? = null
            transaction {
                try {
                    r = findById(id)
                } catch (e: Exception){
                    rollback()
                    throw e
                }
            }
            return r
        }

        override fun selectMany(condition: Op<Boolean>): SizedIterable<SpouseDAO> {
            TODO("Not yet implemented")
        }

        override fun selectAll(): SizedIterable<SpouseDAO> {
            TODO("Not yet implemented")
        }

        override fun update(obj: Spouse) {
            TODO("Not yet implemented")
        }

        override fun remove(id: String) {
            TODO("Not yet implemented")
        }

        override fun removeWhere(condition: Op<Boolean>) {
            TODO("Not yet implemented")
        }
    }

    var singleName by SpouseTable.singleName
    var marriedName by SpouseTable.marriedName
    var personId by SpouseTable.personId
    var birthday by SpouseTable.birthday
    var nationality by SpouseTable.nationality
    val affiliations by AffiliationDAO via SpouseAffiliationTable

    override fun toType(): Spouse? {
        var list = listOf<Affiliation>()
        transaction {
            list = affiliations.map {
                it.toType()!!
            }
        }
        return Spouse(
            id.value,
            singleName,
            marriedName,
            list,
            personId.value,
            birthday,
            nationality
        )
    }
}