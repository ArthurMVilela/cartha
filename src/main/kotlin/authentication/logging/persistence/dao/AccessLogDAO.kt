package authentication.logging.persistence.dao

import authentication.logging.AccessLog
import authentication.logging.Action
import authentication.logging.persistence.AccessLogTable
import authentication.logging.persistence.ActionTable
import newPersistence.DAO
import newPersistence.ResultSet
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class AccessLogDAO:DAO<AccessLog, UUID> {
    override fun insert(obj: AccessLog): AccessLog {
        var inserted:AccessLog? = null

        transaction {
            try {
                val insertedId = AccessLogTable.insertAndGetId {
                    it[id] = obj.id
                    it[sessionId] = obj.sessionId
                    it[userId] = obj.userId
                    it[timestamp] = obj.timestamp
                }
                ActionTable.insertAndGetId {
                    it[logId] = insertedId
                    it[type] = obj.action.type
                    it[subject] = obj.action.subject
                    it[domainId] = obj.action.domainId
                }
                inserted = toType((AccessLogTable innerJoin ActionTable)
                    .select{AccessLogTable.id eq ActionTable.logId and (AccessLogTable.id eq insertedId)}
                    .first())
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return inserted!!
    }

    override fun select(id: UUID): AccessLog? {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>, page: Int, pageLength: Int): ResultSet<AccessLog> {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>): List<AccessLog> {
        TODO("Not yet implemented")
    }

    override fun selectAll(page: Int, pageLength: Int): ResultSet<AccessLog> {
        TODO("Not yet implemented")
    }

    override fun update(obj: AccessLog) {
        TODO("Not yet implemented")
    }

    override fun remove(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun removeWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }

    override fun toType(row: ResultRow): AccessLog {
        val id = row[AccessLogTable.id].value
        val sessionId = row[AccessLogTable.sessionId].value
        val userId = row[AccessLogTable.userId].value
        val timestamp = row[AccessLogTable.timestamp]
        val action = Action(
            row[ActionTable.type],
            row[ActionTable.subject],
            row[ActionTable.domainId]
        )

        return AccessLog(id, sessionId, userId, timestamp, action)
    }
}