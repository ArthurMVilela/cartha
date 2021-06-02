package authentication.persistence.dao

import authentication.Permission
import authentication.User
import authentication.persistence.tables.PermissionTable
import newPersistence.DAO
import newPersistence.ResultSet
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class PermissionDAO:DAO<Permission, Int> {
    override fun insert(obj: Permission): Permission {
        var inserted:Permission? = null
        transaction { 
            try {
                val insertedId = PermissionTable.insertAndGetId {
                    it[userId] = obj.userId
                    it[subject] = obj.subject
                    it[domainId] = obj.domainId
                }
                inserted = toType(PermissionTable.select(Op.build { PermissionTable.id eq insertedId }).first())
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }
        return inserted!!
    }

    override fun select(id: Int): Permission? {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>, page: Int, pageLength: Int): ResultSet<Permission> {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>): List<Permission> {
        var selected = emptyList<Permission>()
        transaction {
            try {
                selected = PermissionTable.select(condition).map {
                    toType(it)
                }
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }
        return selected
    }

    override fun selectAll(page: Int, pageLength: Int): ResultSet<Permission> {
        TODO("Not yet implemented")
    }

    override fun update(obj: Permission) {
        TODO("Not yet implemented")
    }

    override fun remove(id: Int) {
        TODO("Not yet implemented")
    }

    override fun removeWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }

    override fun toType(row: ResultRow): Permission {
        val userId = row[PermissionTable.userId]
        val subject = row[PermissionTable.subject]
        val domainId = row[PermissionTable.domainId]

        return Permission(userId.value, subject, domainId)
    }
}