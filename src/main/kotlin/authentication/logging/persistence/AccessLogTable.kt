package authentication.logging.persistence

import authentication.persistence.tables.UserSessionTable
import authentication.persistence.tables.UserTable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.`java-time`.datetime

object AccessLogTable:UUIDTable("access_log") {
    val sessionId = reference("session_id", UserSessionTable.id)
    val userId = reference("user_id", UserTable.id)
    val timestamp = datetime("timestamp")
}