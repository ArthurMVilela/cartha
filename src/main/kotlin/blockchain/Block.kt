package blockchain

import kotlinx.serialization.Serializable
import util.serializer.LocalDateTimeSerializer
import java.time.LocalDateTime

@Serializable
class Block(
    var id:String?,
    @Serializable(with = LocalDateTimeSerializer::class)
    val timestamp: LocalDateTime,
    val transactions: List<Transaction>,
    val previousHash: String
) {
}