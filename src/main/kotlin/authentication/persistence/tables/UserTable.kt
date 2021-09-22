package authentication.persistence.tables

import authentication.Role
import authentication.UserStatus
import org.jetbrains.exposed.dao.id.UUIDTable

object UserTable:UUIDTable("user") {
    val name = varchar("name", 120)
    val email = varchar("email", 120).uniqueIndex()
    val cpf = char("cpf", 11).nullable()
    val cnpj = char("cnpj", 14).nullable()
    val salt = char("salt", 32)
    val pass = char("pass", 44)
    val role = enumeration("role", Role::class)
    val status = enumeration("status", UserStatus::class)
}