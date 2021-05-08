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
    val userId: String,
    val subject: Subject,
    val domainId: String?
) {
    constructor(userId: String, subject: Subject, domainId: String?): this(null, userId, subject, domainId) {
        id = createId()
    }

    private fun createId():String {
        val md = MessageDigest.getInstance("SHA-256")
        val now = LocalDateTime.now(ZoneOffset.UTC)
        var content = now.toString().toByteArray()
        Thread.sleep(0,2)
        content = content.plus(Random(now.nano).nextBytes(10))
        return Base64.getUrlEncoder().encodeToString(md.digest(content))
    }
}