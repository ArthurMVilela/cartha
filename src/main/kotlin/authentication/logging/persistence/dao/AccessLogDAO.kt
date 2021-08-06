package authentication.logging.persistence.dao

import authentication.logging.AccessLog
import authentication.logging.Action
import authentication.logging.persistence.AccessLogTable
import authentication.logging.persistence.ActionTable
import newPersistence.DAO
import newPersistence.ResultSet
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.math.ceil
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
        var found:AccessLog? = null

        transaction {
            try {
                val row = (AccessLogTable innerJoin ActionTable)
                    .select { AccessLogTable.id eq ActionTable.logId and (AccessLogTable.id eq id) }
                    .firstOrNull()?:return@transaction

                found = toType(row)
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return found
    }

    override fun selectMany(condition: Op<Boolean>, page: Int, pageLength: Int): ResultSet<AccessLog> {
        var numberOfPages = 0
        val results = mutableListOf<AccessLog>()

        transaction {
            try {
                val logsTable = (AccessLogTable innerJoin ActionTable)

                val count = logsTable
                    .select { AccessLogTable.id eq ActionTable.logId }
                    .count()
                numberOfPages = ceil(count/(pageLength * 1.0f)).toInt()
                val rows = logsTable
                    .select { AccessLogTable.id eq ActionTable.logId and condition }
                    .orderBy( AccessLogTable.timestamp, SortOrder.DESC )
                    .limit((pageLength * page) , ((page - 1) * pageLength).toLong())
                rows.forEach {
                    results.add(toType(it))
                }
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return ResultSet(results, page, numberOfPages, pageLength)
    }

    override fun selectMany(condition: Op<Boolean>): List<AccessLog> {
        val result = mutableListOf<AccessLog>()

        transaction {
            try {
                val rows = (AccessLogTable innerJoin ActionTable)
                    .select { AccessLogTable.id eq ActionTable.logId and condition }
                    .orderBy( AccessLogTable.timestamp, SortOrder.DESC )
                rows.forEach {
                    result.add(toType(it))
                }
            } catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return result
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