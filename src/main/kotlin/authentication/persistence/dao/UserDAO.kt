package authentication.persistence.dao

import authentication.User
import authentication.persistence.tables.PermissionTable
import authentication.persistence.tables.UserTable
import newPersistence.DAO
import newPersistence.ResultSet
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UserDAO:DAO<User, UUID> {
    private val permissionDAO = PermissionDAO()
    override fun insert(obj: User): User {
        var inserted:User? = null
        transaction {
            try {
                val insertedId = UserTable.insertAndGetId {
                    it[id] = obj.id
                    it[name] = obj.name
                    it[email] = obj.email
                    it[salt] = obj.salt!!
                    it[pass] = obj.pass!!
                    it[role] = obj.role
                    it[status] = obj.status
                }
                obj.permissions.forEach {
                    permissionDAO.insert(it)
                }
                inserted = toType(UserTable.select(Op.build { UserTable.id eq insertedId}).first())
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }
        return inserted!!
    }

    override fun select(id: UUID): User? {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>, page: Int, pageLength: Int): ResultSet<User> {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>): List<User> {
        TODO("Not yet implemented")
    }

    override fun selectAll(page: Int, pageLength: Int): ResultSet<User> {
        TODO("Not yet implemented")
    }

    override fun update(obj: User) {
        TODO("Not yet implemented")
    }

    override fun remove(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun removeWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }

    override fun toType(row: ResultRow): User {
        val id = row[UserTable.id].value
        val name = row[UserTable.name]
        val email = row[UserTable.email]
        val salt = row[UserTable.salt]
        val pass = row[UserTable.pass]
        val role = row[UserTable.role]
        val permissions = permissionDAO.selectMany(Op.build { PermissionTable.userId eq id }).toHashSet()
        val status = row[UserTable.status]

        return User(id, name, email, salt, pass, role, permissions, status)
    }
}