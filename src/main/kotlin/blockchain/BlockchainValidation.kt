package blockchain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import util.serializer.UUIDSerializer
import java.util.*

@Serializable
class BlockchainValidation(
    val isValid: Boolean,
    @Serializable(with = UUIDSerializer::class)
    @SerialName("corrupted_at_block")
    val corruptedAtBlock: UUID?
) {
}