package authentication.handlers

import authentication.logging.Action
import kotlinx.serialization.Serializable
import util.serializer.LocalDateTimeSerializer
import util.serializer.UUIDSerializer
import java.time.LocalDateTime
import java.util.*

@Serializable
class AccessLogRequestBody(
    @Serializable(with = UUIDSerializer::class)
    val sessionId: UUID,
    val action: Action,
    @Serializable(with = LocalDateTimeSerializer::class)
    val timestamp: LocalDateTime
) {
}