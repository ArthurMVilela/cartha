package authentication.persistence.dao

import authentication.Permission
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

class PermissionDAO(id: EntityID<String>): Entity<String>(id), DAO<Permission> {

    companion object : CompanionDAO<Permission, PermissionDAO, String, IdTable<String>>(PermissionTable) {
        override fun insert(obj: Permission): PermissionDAO {
            var r:PermissionDAO? = null
            transaction {
                try {
                    r = PermissionDAO.new(obj.id!!) {
                        userId = EntityID<String>(obj.userId, UserTable)
                        subject = obj.subject
                        domainId = obj.domainId
                    }
                } catch (e: Exception) {
                    rollback()
                    throw e
                }
            }
            return r!!
        }

        override fun select(id: String): PermissionDAO? {
            var r:PermissionDAO? = null
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

        override fun selectMany(condition: Op<Boolean>): SizedIterable<PermissionDAO> {
            var r:SizedIterable<PermissionDAO> = emptySized()
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

        override fun selectAll(): SizedIterable<PermissionDAO> {
            var r:SizedIterable<PermissionDAO> = emptySized()
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

        override fun update(obj: Permission) {
            transaction {
                try {
                    val found = findById(obj.id!!)!!
                    found.userId = EntityID<String>(obj.userId, UserTable)
                    found.subject = obj.subject
                    found.domainId = obj.domainId
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

    var userId by PermissionTable.userId
    var subject by PermissionTable.subject
    var domainId by PermissionTable.domainId

    override fun toType(): Permission? {
        return Permission(
            userId.value, subject, domainId
        )
    }
}