package newDocument.address

import kotlinx.serialization.Serializable
import util.serializer.UUIDSerializer
import java.util.*

/**
 * Representa um município
 *
 * @param id        identificador único do município
 * @param name      nome do município
 * @param uf        UF do município
 */
@Serializable
class Municipality(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String,
    val uf: UF
)