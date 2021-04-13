package document.persistency.dao.civilRegistry

import document.civilRegistry.Spouse
import document.persistency.dao.DAO
import document.persistency.tables.civilRegistry.spouseAffiliationTable
import document.persistency.tables.civilRegistry.spouseTable
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class SpouseDAO:DAO<Spouse,String> {
    private val affiliationDAO = AffiliationDAO()

    override fun insert(obj: Spouse) {
        try {
            transaction {
                spouseTable.insert {
                    it[id] = obj.id!!
                    it[singleName] = obj.singleName
                    it[marriedName] = obj.marriedName
                    it[personId] = obj.personId
                    it[birthday] = obj.birthday
                    it[nationality] = obj.nationality
                }

                obj.affiliations.forEach {
                    affiliationDAO.insert(it)
                    val affiliation = it
                    spouseAffiliationTable.insert {
                        it[spouseId] = obj.id!!
                        it[affiliationId] = affiliation.id!!
                    }
                }


            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun select(id: String): Spouse? {
        var p: Spouse? = null
        try {
            val results = selectMany(Op.build { spouseTable.id eq id })
            if (!results.isEmpty()) {
                p = selectMany(Op.build { spouseTable.id eq id }).first()
            }
            return p
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun selectMany(condition: Op<Boolean>): List<Spouse> {
        var list:List<Spouse> = listOf()
        try {
            transaction {
                val ids = spouseTable.slice(spouseTable.id).select {
                    condition
                }.map {
                    it[spouseTable.id]
                }

                val affiliations = spouseAffiliationTable.select{
                    spouseAffiliationTable.affiliationId inList ids
                }.map {
                    affiliationDAO.select(it[spouseAffiliationTable.affiliationId])!!
                }

                val found = spouseTable.select {
                    condition
                }.map {
                    Spouse(
                        it[spouseTable.id],
                        it[spouseTable.singleName],
                        it[spouseTable.marriedName],
                        affiliations,
                        it[spouseTable.personId],
                        it[spouseTable.birthday],
                        it[spouseTable.nationality]
                    )
                }
                list = found
            }

            return list
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun selectAll(): List<Spouse> {
        var list:List<Spouse> = listOf()
        try {
            list = selectMany(Op.build { spouseTable.id eq spouseTable.id })
            return list
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun update(oldId: String, new: Spouse) {
        try {
            transaction {
                spouseTable.update({ spouseTable.id eq oldId }) {
                    it[id] = new.id!!
                    it[singleName] = new.singleName
                    it[marriedName] = new.marriedName
                    it[personId] = new.personId
                    it[birthday] = new.birthday
                    it[nationality] = new.nationality
                }

                new.affiliations.forEach {
                    affiliationDAO.update(it.id!!, it)
                    val affiliation = it
                    spouseAffiliationTable.update({
                        spouseAffiliationTable.affiliationId eq affiliation.id!!
                    }) {
                        it[spouseId] = new.id!!
                        it[affiliationId] = affiliation.id!!
                    }
                }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun delete(id: String) {
        try {
            deleteWhere(Op.build { spouseTable.id eq id })
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun deleteWhere(condition: Op<Boolean>) {
        try {
            transaction {
                spouseTable.deleteWhere { condition }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }
}