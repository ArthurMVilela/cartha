package document.persistency.dao.civilRegistry

import document.civilRegistry.Affiliation
import document.persistency.dao.DAO
import document.persistency.tables.civilRegistry.affiliationTable
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class AffiliationDAO:DAO<Affiliation, String> {
    override fun insert(obj: Affiliation) {
        try {
            transaction {
                affiliationTable.insert {
                    it[id] = obj.id!!
                    it[personId] = obj.personId
                    it[name] = obj.name
                    it[UF] = obj.UF
                    it[municipality] = obj.municipality
                }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun select(id: String): Affiliation? {
        var p: Affiliation? = null
        try {
            val results = selectMany(Op.build { affiliationTable.id eq id })
            if (!results.isEmpty()) {
                p = selectMany(Op.build { affiliationTable.id eq id }).first()
            }
            return p
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun selectMany(condition: Op<Boolean>): List<Affiliation> {
        var list:List<Affiliation> = listOf()
        try {
            transaction {
                val found = affiliationTable.select {
                    condition
                }.map {
                    Affiliation(
                        it[affiliationTable.id],
                        it[affiliationTable.personId],
                        it[affiliationTable.name],
                        it[affiliationTable.UF],
                        it[affiliationTable.municipality],
                    )
                }
                list = found
            }

            return list
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun selectAll(): List<Affiliation> {
        var list:List<Affiliation> = listOf()
        try {
            list = selectMany(Op.build { affiliationTable.id eq affiliationTable.id })
            return list
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun update(oldId: String, new: Affiliation) {
        try {
            transaction {
                affiliationTable.update({ affiliationTable.id eq oldId }) {
                    it[id] = new.id!!
                    it[personId] = new.personId
                    it[name] = new.name
                    it[UF] = new.UF
                    it[municipality] = new.municipality
                }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun delete(id: String) {
        try {
            deleteWhere(Op.build { affiliationTable.id eq id })
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun deleteWhere(condition: Op<Boolean>) {
        try {
            transaction {
                affiliationTable.deleteWhere { condition }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }
}