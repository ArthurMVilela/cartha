package authentication.persistence.tables

import authentication.Subject
import document.persistence.tables.PersonTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object PermissionTable:IdTable<String>("permission") {
    override val id: Column<EntityID<String>> = char("id", 44).entityId()
    val userId = reference("user_id", UserTable.id)
    val subject = enumeration("subject", Subject::class)
    val domainId = char("domainId", 44).nullable()

    override val primaryKey: PrimaryKey? = PrimaryKey(PersonTable.id, name="pk_permission_id")
}