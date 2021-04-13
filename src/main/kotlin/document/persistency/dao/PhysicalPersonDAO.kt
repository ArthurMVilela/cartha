package document.persistency.dao

import document.PhysicalPerson
import document.persistency.tables.personTable
import document.persistency.tables.physicalPersonTable
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class PhysicalPersonDAO:DAO<PhysicalPerson, String> {
    private val personDAO = PersonDAO()

    override fun insert(obj: PhysicalPerson) {
        try {
            personDAO.insert(obj)

            transaction {
                physicalPersonTable.insert {
                    it[id] = obj.id!!
                    it[cpf] = obj.cpf
                    it[birthday] = obj.birthDay
                    it[sex] = obj.sex
                    it[color] = obj.color
                    it[civilStatus] = obj.civilStatus
                    it[nationality] = obj.nationality
                }
            }
        } catch (ex:ExposedSQLException) {
            throw ex
        }
    }

    override fun select(id: String): PhysicalPerson? {
        var p: PhysicalPerson? = null
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

    override fun selectMany(condition: Op<Boolean>): List<PhysicalPerson> {
        var list:List<PhysicalPerson> = listOf()
        try {
             transaction {
                 val found = personTable
                     .join(
                         physicalPersonTable,
                         JoinType.INNER,
                         additionalConstraint = { personTable.id eq physicalPersonTable.id }
                     )
                     .select { condition }
                     .map {
                         PhysicalPerson(
                             it[personTable.id],
                             it[personTable.name],
                             it[physicalPersonTable.cpf],
                             it[physicalPersonTable.birthday],
                             it[physicalPersonTable.sex],
                             it[physicalPersonTable.color],
                             it[physicalPersonTable.civilStatus],
                             it[physicalPersonTable.nationality]
                         )
                     }
                 list = found
             }

            return list
        } catch (ex:ExposedSQLException) {
            throw ex
        }
    }

    override fun selectAll(): List<PhysicalPerson> {
        var list:List<PhysicalPerson> = listOf()
        try {
            list = selectMany(Op.build { physicalPersonTable.id eq physicalPersonTable.id })
            return list
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun update(oldId: String, new: PhysicalPerson) {
        try {
            personDAO.update(oldId, new)
            transaction {
                physicalPersonTable.update({ physicalPersonTable.id eq oldId }) {
                    it[cpf] = new.cpf
                    it[birthday] = new.birthDay
                    it[sex] = new.sex
                    it[color] = new.color
                    it[civilStatus] = new.civilStatus
                    it[nationality] = new.nationality
                }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun delete(id: String) {
        try {
            deleteWhere(Op.build { physicalPersonTable.id eq id })
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }

    override fun deleteWhere(condition: Op<Boolean>) {
        try {
            transaction {
                val rows = physicalPersonTable
                    .slice(physicalPersonTable.id)
                    .select { condition }
                    .map {
                        it[physicalPersonTable.id]
                    }
                physicalPersonTable.deleteWhere { condition }
                rows.forEach {
                    personDAO.deleteWhere(Op.build { personTable.id eq it })
                }
            }
        } catch (ex: ExposedSQLException) {
            throw ex
        }
    }
}