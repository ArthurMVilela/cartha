package authentication.logging.persistence

import authentication.Subject
import authentication.logging.ActionType
import org.jetbrains.exposed.dao.id.IntIdTable

object ActionTable:IntIdTable("action") {
    val logId = reference("log_id", AccessLogTable.id)
    val type = enumeration("type", ActionType::class)
    val subject = enumeration("subject", Subject::class)
    val domainId = uuid("domain_id").nullable()
}