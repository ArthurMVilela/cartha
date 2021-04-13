package document.persistency.dao.civilRegistry

import document.civilRegistry.Grandparent
import document.persistency.dao.DAO
import document.persistency.tables.civilRegistry.grandparentTable
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class GrandparentDAO:DAO<Grandparent,String> {
    override fun insert(obj: Grandparent) {
        try {
            transaction {
                grandparentTable.insert {
                    it[id] = obj.id!!
                    it[personId] = obj.personId
                    it[name] = obj.name
                    it[type] = obj.type
                    it[UF] = obj.UF
                    it[municipality] = obj.municipality
                }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun select(id: String): Grandparent? {
        var p: Grandparent? = null
        try {
            val results = selectMany(Op.build { grandparentTable.id eq id })
            if (!results.isEmpty()) {
                p = selectMany(Op.build { grandparentTable.id eq id }).first()
            }
            return p
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun selectMany(condition: Op<Boolean>): List<Grandparent> {
        var list:List<Grandparent> = listOf()
        try {
            transaction {
                val found = grandparentTable.select {
                    condition
                }.map {
                    Grandparent(
                        it[grandparentTable.id],
                        it[grandparentTable.personId],
                        it[grandparentTable.name],
                        it[grandparentTable.type],
                        it[grandparentTable.UF],
                        it[grandparentTable.municipality],
                    )
                }
                list = found
            }

            return list
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun selectAll(): List<Grandparent> {
        var list:List<Grandparent> = listOf()
        try {
            list = selectMany(Op.build { grandparentTable.id eq grandparentTable.id })
            return list
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun update(oldId: String, new: Grandparent) {
        try {
            transaction {
                grandparentTable.update({ grandparentTable.id eq oldId }) {
                    it[id] = new.id!!
                    it[personId] = new.personId
                    it[name] = new.name
                    it[type] = new.type
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
            deleteWhere(Op.build { grandparentTable.id eq id })
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun deleteWhere(condition: Op<Boolean>) {
        try {
            transaction {
                grandparentTable.deleteWhere { condition }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }
}