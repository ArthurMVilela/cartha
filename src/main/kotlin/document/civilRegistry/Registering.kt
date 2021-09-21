package document.civilRegistry

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import util.serializer.LocalDateTimeSerializer
import util.serializer.UUIDSerializer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Representa uma observação abverbação de um documento de registro civil
 */
@Serializable
class Registering(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @Serializable(with = UUIDSerializer::class)
    @SerialName("document_id")
    val documentId: UUID,
    @Serializable(with = LocalDateTimeSerializer::class)
    val timestamp: LocalDateTime,
    val text: String
) {
    override fun toString(): String {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy (HH:mm)")
        return "${formatter.format(timestamp)} - $text"
    }
}