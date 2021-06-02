package authentication.persistence.tables

import authentication.Subject
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object PermissionTable:IntIdTable("permission") {
    val userId = reference("user_id", UserTable.id, onDelete = ReferenceOption.CASCADE)
    val subject = enumeration("subject", Subject::class)
    val domainId = uuid("domain_id").nullable()
}