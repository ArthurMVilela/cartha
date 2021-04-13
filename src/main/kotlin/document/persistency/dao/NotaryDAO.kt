package document.persistency.dao

import document.Notary
import document.persistency.tables.notaryTable
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class NotaryDAO:DAO<Notary, String> {
    override fun insert(obj: Notary) {
        try {
            transaction {
                notaryTable.insert {
                    it[id] = obj.id!!
                    it[name] = obj.name
                    it[cnpj] = obj.cnpj
                    it[cns] = obj.cns
                }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun select(id: String): Notary? {
        var p: Notary? = null
        try {
            val results = selectMany(Op.build { notaryTable.id eq id })
            if (!results.isEmpty()) {
                p = selectMany(Op.build { notaryTable.id eq id }).first()
            }
            return p
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun selectMany(condition: Op<Boolean>): List<Notary> {
        var list:List<Notary> = listOf()
        try {
            transaction {
                val found = notaryTable.select {
                    condition
                }.map {
                    Notary(
                        it[notaryTable.id],
                        it[notaryTable.name],
                        it[notaryTable.cnpj],
                        it[notaryTable.cns],
                    )
                }
                list = found
            }

            return list
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun selectAll(): List<Notary> {
        var list:List<Notary> = listOf()
        try {
            list = selectMany(Op.build { notaryTable.id eq notaryTable.id })
            return list
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun update(oldId: String, new: Notary) {
        try {
            transaction {
                notaryTable.update({ notaryTable.id eq oldId }) {
                    it[name] = new.name
                    it[cnpj] = new.cnpj
                    it[cns] = new.cns
                }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun delete(id: String) {
        try {
            deleteWhere(Op.build { notaryTable.id eq id })
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun deleteWhere(condition: Op<Boolean>) {
        try {
            transaction {
                notaryTable.deleteWhere { condition }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }
}