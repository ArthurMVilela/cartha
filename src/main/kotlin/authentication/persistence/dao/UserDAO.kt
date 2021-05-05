package authentication.persistence.dao

import authentication.Permission
import authentication.User
import authentication.persistence.tables.PermissionTable
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

class UserDAO(id: EntityID<String>): Entity<String>(id), DAO<User> {
    companion object: CompanionDAO<User, UserDAO, String, IdTable<String>>(UserTable) {
        override fun insert(obj: User): UserDAO {
            var r:UserDAO? = null
            transaction {
                try {
                    r = new(obj.id!!) {
                        name = obj.name
                        email = obj.email
                        cpf = obj.cpf
                        salt = obj.salt!!
                        pass = obj.pass!!
                        role = obj.role

                        obj.permissions.forEach {
                            PermissionDAO.insert(it)
                        }
                    }
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
            return r!!
        }

        override fun select(id: String): UserDAO? {
            var r:UserDAO? = null
            transaction {
                try {
                    r = findById(id)
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
            return r
        }

        override fun selectMany(condition: Op<Boolean>): SizedIterable<UserDAO> {
            var r:SizedIterable<UserDAO> = emptySized()
            transaction {
                try {
                    r = find(condition)
                } catch (e:Exception){
                    rollback()
                    throw e
                }
            }
            return r
        }

        override fun selectAll(): SizedIterable<UserDAO> {
            var r:SizedIterable<UserDAO> = emptySized()
            transaction {
                try {
                    r = all()
                } catch (e:Exception){
                    rollback()
                    throw e
                }
            }
            return r
        }

        override fun update(obj: User) {
            transaction {
                try {
                    val found = findById(obj.id!!)!!
                    found.name = obj.name
                    found.email = obj.email
                    found.cpf = obj.cpf
                    found.salt = obj.salt!!
                    found.pass = obj.pass!!
                    found.role = obj.role

                    PermissionDAO.removeWhere(Op.build { PermissionTable.userId eq obj.id })

                    obj.permissions.forEach {
                        PermissionDAO.insert(it)
                    }
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
        }

        override fun remove(id: String) {
            transaction {
                try {
                    PermissionDAO.removeWhere(Op.build { PermissionTable.userId eq id })

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
                        PermissionDAO.removeWhere(Op.build { PermissionTable.userId eq it.id })
                        it.delete()
                    }
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
        }
    }

    var name by UserTable.name
    var email by UserTable.email
    var cpf by UserTable.cpf
    var salt by UserTable.salt
    var pass by UserTable.pass
    var role by UserTable.role
    val permissions by PermissionDAO referrersOn PermissionTable.userId

    override fun toType(): User? {
        var permissionList:List<Permission> = listOf()
        transaction {
            permissionList = permissions.map { it.toType()!! }
        }
        return User(
            id.value, name, email, cpf, salt, pass, role, permissionList
        )
    }
}