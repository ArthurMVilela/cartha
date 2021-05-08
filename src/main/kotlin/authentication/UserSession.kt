package authentication

import kotlinx.serialization.Serializable
import util.serializer.LocalDateTimeSerializer
import util.serializer.UUIDSerializer
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import kotlin.random.Random

/**
 * Representa uma sessão de usuário
 */
@Serializable
class UserSession(
    @Serializable(with = UUIDSerializer::class)
    var id:UUID?,
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    val userRole: Role,
    val userPermissions: List<Permission>,
    @Serializable(with = LocalDateTimeSerializer::class)
    val start: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    var end: LocalDateTime?
) {
    constructor(user: User, start:LocalDateTime):this(null, user.id!!, user.role, user.permissions, start, null){
        id = createId()
    }

    fun isAuthorized(role: Role?, subject: Subject?,  domainId: String?): Boolean {
        if (role == null) {
            return true
        }

        if (role != userRole) {
            println("role != userRole")
            return false
        }

        if (subject == null){
            println("subject == null")
            return false
        }

        println(userPermissions.firstOrNull { p -> p.subject == subject }?.id)
        val permission = userPermissions.firstOrNull { p -> p.subject == subject } ?: return false

        if (permission.domainId != domainId) {
            return false
        }

        return true
    }

    fun endSession(time: LocalDateTime) {
        end = time
    }

    private fun createId():UUID {
        return UUID.randomUUID()
    }
}