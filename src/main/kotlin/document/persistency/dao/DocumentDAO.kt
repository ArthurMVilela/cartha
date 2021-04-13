package document.persistency.dao

import document.Document
import document.persistency.tables.documentTable
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class DocumentDAO:DAO<Document, String> {
    override fun insert(obj: Document) {
        try {
            transaction {
                documentTable.insert {
                    it[id] = obj.id!!
                    it[status] = obj.status
                    it[officialId] = obj.officialId
                    it[notaryId] = obj.notaryId
                }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun select(id: String): Document? {
        var p: Document? = null
        try {
            val results = selectMany(Op.build { documentTable.id eq id })
            if (!results.isEmpty()) {
                p = selectMany(Op.build { documentTable.id eq id }).first()
            }
            return p
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun selectMany(condition: Op<Boolean>): List<Document> {
        var list:List<Document> = listOf()
        try {
            transaction {
                val found = documentTable.select {
                    condition
                }.map {
                    object : Document() {
                        override val id = it[documentTable.id]
                        override val status = it[documentTable.status]
                        override val officialId = it[documentTable.officialId]
                        override val notaryId = it[documentTable.notaryId]
                    }
                }
                list = found
            }

            return list
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun selectAll(): List<Document> {
        var list:List<Document> = listOf()
        try {
            list = selectMany(Op.build { documentTable.id eq documentTable.id })
            return list
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun update(oldId: String, new: Document) {
        try {
            transaction {
                documentTable.update({ documentTable.id eq oldId }) {
                    it[status] = new.status
                    it[officialId] = new.officialId
                    it[notaryId] = new.notaryId
                }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun delete(id: String) {
        try {
            deleteWhere(Op.build { documentTable.id eq id })
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun deleteWhere(condition: Op<Boolean>) {
        try {
            transaction {
                documentTable.deleteWhere { condition }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }
}