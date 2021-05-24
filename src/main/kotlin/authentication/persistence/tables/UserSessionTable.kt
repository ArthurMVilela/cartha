package authentication.persistence.tables

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.`java-time`.datetime

object UserSessionTable:UUIDTable("user_session") {
    val userId = reference("user_id", UserTable.id)
    val start = datetime("start")
    val end = datetime("end").nullable()
}