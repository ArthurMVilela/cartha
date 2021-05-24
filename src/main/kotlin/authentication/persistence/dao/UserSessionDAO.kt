package authentication.persistence.dao

import authentication.UserSession
import authentication.persistence.tables.UserSessionTable
import newPersistence.DAO
import newPersistence.ResultSet
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UserSessionDAO:DAO<UserSession, UUID> {
    private val userDAO = UserDAO()

    override fun insert(obj: UserSession): UserSession {
        var inserted:UserSession? = null

        transaction {
            try {
                val insertedId = UserSessionTable.insertAndGetId {
                    it[id] = obj.id
                    it[userId] = obj.user.id
                    it[start] = obj.start
                    it[end] = obj.end
                }
                inserted = toType(UserSessionTable.select(Op.build { UserSessionTable.id eq insertedId }).first())
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return inserted!!
    }

    override fun select(id: UUID): UserSession? {
        var found:UserSession? = null

        transaction {
            try {
                val foundRow = UserSessionTable.select(Op.build { UserSessionTable.id eq id }).first()
                found = toType(foundRow)
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return found
    }

    override fun selectMany(condition: Op<Boolean>, page: Int, pageLength: Int): ResultSet<UserSession> {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>): List<UserSession> {
        TODO("Not yet implemented")
    }

    override fun selectAll(page: Int, pageLength: Int): ResultSet<UserSession> {
        TODO("Not yet implemented")
    }

    override fun update(obj: UserSession) {
        transaction {
            try {
                UserSessionTable.update({UserSessionTable.id eq obj.id}) {
                    with(SqlExpressionBuilder) {
                        it[userId] = obj.user.id
                        it[start] = obj.start
                        it[end] = obj.end
                    }
                }
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }
    }

    override fun remove(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun removeWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }

    override fun toType(row: ResultRow): UserSession {
        val id = row[UserSessionTable.id].value
        val user = userDAO.select(row[UserSessionTable.userId].value)?:throw NullPointerException("Usuário não existe.")
        val start = row[UserSessionTable.start]
        val end = row[UserSessionTable.end]

        return UserSession(id, user, start, end)
    }
}