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
    @Serializable(with = UUIDSerializer::class)
    var id: UUID?,
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    val subject: Subject,
    val domainId: String?
) {
    constructor(userId: UUID, subject: Subject, domainId: String?): this(null, userId, subject, domainId) {
        id = createId()
    }

    private fun createId():UUID {
        return UUID.randomUUID()
    }
}