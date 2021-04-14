package document.persistency.dao

import document.Person
import document.persistency.tables.personTable
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class PersonDAO : DAO<Person, String> {
    override fun insert(obj: Person) {
        try {
            transaction {
                personTable.insert {
                    it[id] = obj.id!!
                    it[name] = obj.name
                }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun select(id: String): Person? {
        var p: Person? = null
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

    override fun selectMany(condition: Op<Boolean>): List<Person> {
        var list:List<Person> = listOf()
        try {
            transaction {
                val found = personTable.select {
                    condition
                }.map {
                    object : Person() {
                        override var id:String? = it[personTable.id]
                        override val name = it[personTable.name]
                    }
                }
                list = found
            }

            return list
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun selectAll(): List<Person> {
        var list:List<Person> = listOf()
        try {
            list = selectMany(Op.build { personTable.id eq personTable.id })
            return list
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun update(oldId: String, new: Person) {
        try {
            transaction {
                personTable.update({ personTable.id eq oldId }) {
                    it[name] = new.name
                }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun delete(id: String) {
        try {
            deleteWhere(Op.build { personTable.id eq id })
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun deleteWhere(condition: Op<Boolean>) {
        try {
            transaction {
                personTable.deleteWhere { condition }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

}