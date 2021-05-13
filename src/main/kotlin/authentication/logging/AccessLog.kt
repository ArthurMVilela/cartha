package authentication.logging

import kotlinx.serialization.Serializable
import util.serializer.LocalDateTimeSerializer
import util.serializer.UUIDSerializer
import java.time.LocalDateTime
import java.util.*

/**
 * Representa um log de acesso
 */
@Serializable
class AccessLog (
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @Serializable(with = UUIDSerializer::class)
    val sessionId: UUID,
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    @Serializable(with = LocalDateTimeSerializer::class)
    val timestamp: LocalDateTime,
    val action: Action
){
}