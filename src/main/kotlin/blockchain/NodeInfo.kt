package blockchain

import kotlinx.serialization.Serializable
import util.serializer.UUIDSerializer
import java.util.*

/**
 * Representa informações sobre um nó de blockchain
 */
@Serializable
class NodeInfo(
    @Serializable(with = UUIDSerializer::class)
    val nodeId: UUID,
    @Serializable(with = UUIDSerializer::class)
    val notaryId: UUID,
    val address: String,
    val status: NodeStatus = NodeStatus.Unknown
) {
}