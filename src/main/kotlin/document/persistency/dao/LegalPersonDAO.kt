package document.persistency.dao

import document.LegalPerson
import document.persistency.tables.legalPersonTable
import document.persistency.tables.personTable
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class LegalPersonDAO:DAO<LegalPerson, String> {
    private val personDAO = PersonDAO()

    override fun insert(obj: LegalPerson) {
        try {
            personDAO.insert(obj)

            transaction {
                legalPersonTable.insert {
                    it[id] = obj.id!!
                    it[cnpj] = obj.cnpj
                }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun select(id: String): LegalPerson? {
        var p: LegalPerson? = null
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

    override fun selectMany(condition: Op<Boolean>): List<LegalPerson> {
        var list:List<LegalPerson> = listOf()
        try {
            transaction {
                val found = personTable
                    .join(
                        legalPersonTable,
                        JoinType.INNER,
                        additionalConstraint = { personTable.id eq legalPersonTable.id }
                    )
                    .select { condition }
                    .map {
                        LegalPerson(
                            it[personTable.id],
                            it[personTable.name],
                            it[legalPersonTable.cnpj],
                        )
                    }
                list = found
            }

            return list
        } catch (ex:ExposedSQLException) {
            throw ex
        }
    }

    override fun selectAll(): List<LegalPerson> {
        var list:List<LegalPerson> = listOf()
        try {
            list = selectMany(Op.build { legalPersonTable.id eq legalPersonTable.id })
            return list
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun update(oldId: String, new: LegalPerson) {
        try {
            personDAO.update(oldId, new)
            transaction {
                legalPersonTable.update({ legalPersonTable.id eq oldId }) {
                    it[cnpj] = new.cnpj
                }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun delete(id: String) {
        try {
            deleteWhere(Op.build { legalPersonTable.id eq id })
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun deleteWhere(condition: Op<Boolean>) {
        try {
            transaction {
                val rows = legalPersonTable
                    .slice(legalPersonTable.id)
                    .select { condition }
                    .map {
                        it[legalPersonTable.id]
                    }
                legalPersonTable.deleteWhere { condition }
                rows.forEach {
                    personDAO.deleteWhere(Op.build { personTable.id eq it })
                }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }
}