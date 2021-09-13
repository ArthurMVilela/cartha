package newDocument.address

import kotlinx.serialization.Serializable

/**
 * Representa um município
 *
 * @param id        identificador único do município
 * @param name      nome do município
 * @param uf        UF do município
 */
@Serializable
class Municipality(
    var id: Int? = null,
    val name: String,
    val uf: UF
)