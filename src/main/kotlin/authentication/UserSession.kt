package authentication

import kotlinx.serialization.Serializable
import util.serializer.LocalDateTimeSerializer
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
    var id:String?,
    val userId: String,
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
            return false
        }

        if (subject == null){
            return false
        }

        val permission = userPermissions.firstOrNull { p -> p.subject == subject } ?: return false

        if (permission.domainId != domainId) {
            return false
        }

        return true
    }

    fun endSession(time: LocalDateTime) {
        end = time
    }

    private fun createId():String {
        val md = MessageDigest.getInstance("SHA")
        val now = LocalDateTime.now(ZoneOffset.UTC)
        var content = now.toString().toByteArray()
        content = content.plus(Random(now.toEpochSecond(ZoneOffset.UTC)).nextBytes(10))
        return Base64.getUrlEncoder().encodeToString(md.digest(content))
    }
}