package blockchain.handlers

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import util.serializer.UUIDSerializer
import java.util.*

@Serializable
class AddNodeRequest(
    @Serializable(with = UUIDSerializer::class)
    @SerialName("notary_id")
    val notaryId: UUID,
    val address: String
) {
}