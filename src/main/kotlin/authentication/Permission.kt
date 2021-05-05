package authentication

import kotlinx.serialization.Serializable
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
    var id: String?,
    val subject: Subject,
    val domainId: String?
) {
    constructor(subject: Subject, domainId: String?): this(null, subject, domainId) {
        id = createId()
    }

    private fun createId():String {
        val md = MessageDigest.getInstance("SHA-256")
        val now = LocalDateTime.now(ZoneOffset.UTC)
        var content = now.toString().toByteArray()
        content = content.plus(Random(now.toEpochSecond(ZoneOffset.UTC)).nextBytes(10))
        return Base64.getUrlEncoder().encodeToString(md.digest(content))
    }
}