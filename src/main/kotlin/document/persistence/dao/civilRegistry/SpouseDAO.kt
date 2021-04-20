package document.persistence.dao.civilRegistry

import document.civilRegistry.Affiliation
import document.civilRegistry.Spouse
import persistence.CompanionDAO
import persistence.DAO
import document.persistence.dao.PhysicalPersonDAO
import document.persistence.tables.civilRegistry.SpouseAffiliationTable
import document.persistence.tables.civilRegistry.SpouseTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

class SpouseDAO(id:EntityID<String>):Entity<String>(id), DAO<Spouse> {
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
//                    obj.affiliations.forEach {
//                        val inserted = AffiliationDAO.insert(it)
//                        SpouseAffiliationTable.insert {
//                            it[spouseId] = obj.id!!
//                            it[affiliationId] = inserted.id
//                        }
//                    }
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
            transaction {
                try {
                    val found = findById(obj.id!!)!!
                    found.singleName = obj.singleName
                    found.marriedName = obj.marriedName
                    found.personId = PhysicalPersonDAO.select(obj.personId)!!.id
                    found.birthday = obj.birthday
                    found.nationality = obj.nationality

                    val foundAffiliations = found.affiliations.toList().map { it }
                    SpouseAffiliationTable.deleteWhere { SpouseAffiliationTable.spouseId eq obj.id!! }
                    foundAffiliations.forEach {
                        it.delete()
                    }
                    obj.affiliations.forEach {
                        AffiliationDAO.insert(it)
                    }


                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
        }

        override fun remove(id: String) {
            transaction {
                try {
                    val found = findById(id)
                    SpouseAffiliationTable.deleteWhere { SpouseAffiliationTable.spouseId eq found!!.id }
                    found?.delete()
                    found?.affiliations!!.forEach {
                        it.delete()
                    }

                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
        }

        override fun removeWhere(condition: Op<Boolean>) {
            transaction {
                try {
                    find(condition).forEach {
                        remove(it.id.value)
                    }
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
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