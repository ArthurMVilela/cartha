package document

import kotlinx.serialization.Serializable

/**
 * Representa um documento.
 *
 * @property id         identificado único
 * @property status     estado do documento
 * @property official   Oficial que autorizou e criou documento
 */
@Serializable
abstract class Document(
    val id: String,
    val status: DocumentStatus,
    val official: String
)