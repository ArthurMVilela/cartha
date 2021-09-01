package blockchain

import kotlinx.serialization.Serializable
import util.serializer.LocalDateTimeSerializer
import util.serializer.UUIDSerializer
import java.time.LocalDateTime
import java.util.*

@Serializable
class BlockInfo(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @Serializable(with = LocalDateTimeSerializer::class)
    val timestamp: LocalDateTime
) {

}