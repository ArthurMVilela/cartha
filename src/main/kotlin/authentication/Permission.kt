package authentication

import kotlinx.serialization.Serializable
import util.serializer.UUIDSerializer
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import kotlin.random.Random

/**
 * Representa uma permissão para o controle de acesso
 */
@Serializable
class Permission(
    val subject: Subject,
    val domainId: String?
) {

    override fun equals(other: Any?): Boolean {
        other as Permission
        return other.subject == subject && other.domainId == domainId
    }

    override fun hashCode(): Int {
        var result = subject.hashCode()
        result = 31 * result + (domainId?.hashCode() ?: 0)
        return result
    }
}