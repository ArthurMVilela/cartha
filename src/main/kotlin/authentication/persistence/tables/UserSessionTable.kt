package authentication.persistence.tables

import authentication.Role
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.`java-time`.datetime

object UserSessionTable:IdTable<String>("user_session") {
    override val id: Column<EntityID<String>> = char("id", 44).entityId()
    val userId = reference("user_id", UserTable.id)
    val userRole = enumeration("user_role", Role::class)
    val start = datetime("start")
    var end = datetime("end").nullable()

    override val primaryKey: PrimaryKey? = PrimaryKey(id, name="pk_user_session_id")
}