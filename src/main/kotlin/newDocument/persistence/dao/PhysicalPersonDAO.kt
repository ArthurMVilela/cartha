package newDocument.persistence.dao

import newDocument.persistence.tables.PersonTable
import newDocument.persistence.tables.PhysicalPersonTable
import newDocument.person.PhysicalPerson
import newPersistence.DAO
import newPersistence.ResultSet
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class PhysicalPersonDAO:DAO<PhysicalPerson,UUID> {

    lateinit var table:Join

    init {
        transaction {
            table = PhysicalPersonTable.join(PersonTable, JoinType.INNER, additionalConstraint = {PhysicalPersonTable.id eq PersonTable.id})
        }
    }

    override fun insert(obj: PhysicalPerson): PhysicalPerson {
        var inserted:PhysicalPerson? = null

        transaction {
            try {
                val insertedId = PersonTable.insertAndGetId {
                    it[id] = obj.id
                    it[name] = obj.name
                    it[accountId] = obj.accountId
                }
                PhysicalPersonTable.insert {
                    it[id] = insertedId
                    it[cpf] = obj.cpf
                    it[birthday] = obj.birthday
                    it[sex] = obj.sex
                    it[color] = obj.color
                    it[civilStatus] = obj.civilStatus
                    it[nationality] = obj.nationality
                }

                inserted = toType(table.select { Op.build { PhysicalPersonTable.id eq insertedId } }.first())
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return inserted!!
    }

    override fun select(id: UUID): PhysicalPerson? {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>, page: Int, pageLength: Int): ResultSet<PhysicalPerson> {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>): List<PhysicalPerson> {
        TODO("Not yet implemented")
    }

    override fun selectAll(page: Int, pageLength: Int): ResultSet<PhysicalPerson> {
        TODO("Not yet implemented")
    }

    override fun update(obj: PhysicalPerson) {
        TODO("Not yet implemented")
    }

    override fun remove(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun removeWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }

    override fun toType(row: ResultRow): PhysicalPerson {
        val id = row[PhysicalPersonTable.id].value
        val accountId = row[PersonTable.accountId]
        val name = row[PersonTable.name]
        val cpf = row[PhysicalPersonTable.cpf]
        val birthday = row[PhysicalPersonTable.birthday]
        val sex = row[PhysicalPersonTable.sex]
        val color = row[PhysicalPersonTable.color]
        val civilStatus = row[PhysicalPersonTable.civilStatus]
        val nationality = row[PhysicalPersonTable.nationality]

        return PhysicalPerson(id, accountId, name, cpf, birthday, sex, color, civilStatus, nationality)
    }
}