package document.persistency.dao.civilRegistry

import document.civilRegistry.Twin
import document.persistency.dao.DAO
import document.persistency.tables.civilRegistry.twinTable
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class TwinDAO:DAO<Twin, String> {
    override fun insert(obj: Twin) {
        try {
            transaction {
                twinTable.insert {
                    it[id] = obj.id!!
                    it[birthCertificateId] = obj.birthCertificateId
                    it[registration] = obj.registration
                    it[name] = obj.name
                }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun select(id: String): Twin? {
        var p: Twin? = null
        try {
            val results = selectMany(Op.build { twinTable.id eq id })
            if (!results.isEmpty()) {
                p = selectMany(Op.build { twinTable.id eq id }).first()
            }
            return p
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun selectMany(condition: Op<Boolean>): List<Twin> {
        var list:List<Twin> = listOf()
        try {
            transaction {
                val found = twinTable.select {
                    condition
                }.map {
                    Twin(
                        it[twinTable.id],
                        it[twinTable.birthCertificateId],
                        it[twinTable.registration],
                        it[twinTable.name]
                    )
                }
                list = found
            }

            return list
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun selectAll(): List<Twin> {
        var list:List<Twin> = listOf()
        try {
            list = selectMany(Op.build { twinTable.id eq twinTable.id })
            return list
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun update(oldId: String, new: Twin) {
        try {
            transaction {
                twinTable.update({ twinTable.id eq oldId }) {
                    it[birthCertificateId] = new.birthCertificateId
                    it[registration] = new.registration
                    it[name] = new.name
                }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun delete(id: String) {
        try {
            deleteWhere(Op.build { twinTable.id eq id })
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun deleteWhere(condition: Op<Boolean>) {
        try {
            transaction {
                twinTable.deleteWhere { condition }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }
}