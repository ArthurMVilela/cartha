package authentication.persistence.dao

import authentication.Permission
import authentication.User
import authentication.UserSession
import authentication.persistence.tables.PermissionTable
import authentication.persistence.tables.UserSessionTable
import authentication.persistence.tables.UserTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.emptySized
import org.jetbrains.exposed.sql.transactions.transaction
import persistence.CompanionDAO
import persistence.DAO
import java.lang.Exception

class UserSessionDAO(id: EntityID<String>): Entity<String>(id), DAO<UserSession> {
    companion object: CompanionDAO<UserSession, UserSessionDAO, String, IdTable<String>>(UserSessionTable) {
        override fun insert(obj: UserSession): UserSessionDAO {
            var r:UserSessionDAO? = null
            transaction {
                try {
                    r = new(obj.id!!) {
                        userId = EntityID<String>(obj.userId, UserTable)
                        userRole = obj.userRole
                        start = obj.start
                        end = obj.end

                        permissions = PermissionDAO.selectMany(Op.build { PermissionTable.userId eq obj.userId })
                            .map { it.toType()!! }
                    }
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
            return r!!
        }

        override fun select(id: String): UserSessionDAO? {
            var r:UserSessionDAO? = null
            transaction {
                try {
                    r = findById(id)
                    r?.permissions = PermissionDAO.selectMany(Op.build { PermissionTable.userId eq r!!.userId })
                        .map { it.toType()!! }
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
            return r
        }

        override fun selectMany(condition: Op<Boolean>): SizedIterable<UserSessionDAO> {
            var r:SizedIterable<UserSessionDAO> = emptySized()
            transaction {
                try {
                    r = find(condition)
                    r.forEach {
                        it.permissions = PermissionDAO.selectMany(Op.build { PermissionTable.userId eq it.userId })
                            .map { p -> p.toType()!! }
                    }
                } catch (e:Exception){
                    rollback()
                    throw e
                }
            }
            return r
        }

        override fun selectAll(): SizedIterable<UserSessionDAO> {
            var r:SizedIterable<UserSessionDAO> = emptySized()
            transaction {
                try {
                    r = all()
                    r.forEach {
                        it.permissions = PermissionDAO.selectMany(Op.build { PermissionTable.userId eq it.userId })
                            .map { p -> p.toType()!! }
                    }
                } catch (e:Exception){
                    rollback()
                    throw e
                }
            }
            return r
        }

        override fun update(obj: UserSession) {
            transaction {
                try {
                    val found = findById(obj.id!!)!!
                    found.userId = EntityID<String>(obj.userId, UserTable)
                    found.userRole = obj.userRole
                    found.start = obj.start
                    found.end = obj.end
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
        }

        override fun remove(id: String) {
            transaction {
                try {
                    findById(id)?.delete()
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
        }

        override fun removeWhere(condition: Op<Boolean>) {
            transaction {
                try {
                    find(condition).forEach {
                        it.delete()
                    }
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
        }
    }

    var userId by UserSessionTable.userId
    var userRole by UserSessionTable.userRole
    var start by UserSessionTable.start
    var end by UserSessionTable.end
    var permissions:List<Permission> = listOf()

    override fun toType(): UserSession? {
        return UserSession(
            id.value, userId.value, userRole, permissions, start, end
        )
    }
}