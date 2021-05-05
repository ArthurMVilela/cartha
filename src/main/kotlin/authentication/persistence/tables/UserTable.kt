package authentication.persistence.tables

import authentication.Role
import document.persistence.tables.PersonTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object UserTable: IdTable<String>("user") {
    override val id: Column<EntityID<String>> = char("id", 44).entityId()
    val name = varchar("name", 120)
    val email = varchar("email", 120).nullable()
    val cpf = char("cpf", 11).nullable()
    val salt = char("salt", 32)
    val pass = char("pass", 44)
    val role = enumeration("role", Role::class)

    override val primaryKey: PrimaryKey? = PrimaryKey(PersonTable.id, name="pk_user_id")
}