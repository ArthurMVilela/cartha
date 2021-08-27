package blockchain

import kotlinx.serialization.Serializable
import util.serializer.LocalDateTimeSerializer
import util.serializer.UUIDSerializer
import java.time.LocalDateTime
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
    var status: NodeStatus = NodeStatus.Unknown,
    @Serializable(with = LocalDateTimeSerializer::class)
    var lastHealthCheck: LocalDateTime? = null
) {

    constructor(notaryId: UUID, address: String, lastHealthCheck: LocalDateTime?) : this (
        createId(),
        notaryId,
        address,
        NodeStatus.Unknown,
        lastHealthCheck
    )

    companion object {
        /**
         * Cria o identificador único para o nó
         *
         * @return UUID para a id do nó
         */
        private fun createId():UUID {
            return UUID.randomUUID()
        }
    }
}