package document.persistency.dao

import document.Official
import document.persistency.tables.officialTable
import document.persistency.tables.personTable
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class OfficialDAO:DAO<Official, String> {
    private val personDAO = PersonDAO()

    override fun insert(obj: Official) {
        try {
            personDAO.insert(obj)

            transaction {
                officialTable.insert {
                    it[id] = obj.id!!
                    it[cpf] = obj.cpf
                    it[sex] = obj.sex
                }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun select(id: String): Official? {
        var p: Official? = null
        try {
            val results = selectMany(Op.build { personTable.id eq id })
            if (!results.isEmpty()) {
                p = selectMany(Op.build { personTable.id eq id }).first()
            }
            return p
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun selectMany(condition: Op<Boolean>): List<Official> {
        var list:List<Official> = listOf()
        try {
            transaction {
                val found = personTable
                    .join(
                        officialTable,
                        JoinType.INNER,
                        additionalConstraint = { personTable.id eq officialTable.id }
                    )
                    .select { condition }
                    .map {
                        Official(
                            it[personTable.id],
                            it[personTable.name],
                            it[officialTable.cpf],
                            it[officialTable.sex],
                        )
                    }
                list = found
            }

            return list
        } catch (ex:ExposedSQLException) {
            throw ex
        }
    }

    override fun selectAll(): List<Official> {
        var list:List<Official> = listOf()
        try {
            list = selectMany(Op.build { officialTable.id eq officialTable.id })
            return list
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun update(oldId: String, new: Official) {
        try {
            personDAO.update(oldId, new)
            transaction {
                officialTable.update({ officialTable.id eq oldId }) {
                    it[cpf] = new.cpf
                    it[sex] = new.sex
                }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun delete(id: String) {
        TODO("Not yet implemented")
    }

    override fun deleteWhere(condition: Op<Boolean>) {
        try {
            transaction {
                val rows = officialTable
                    .slice(officialTable.id)
                    .select { condition }
                    .map {
                        it[officialTable.id]
                    }
                officialTable.deleteWhere { condition }
                rows.forEach {
                    personDAO.deleteWhere(Op.build { personTable.id eq it })
                }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }
}