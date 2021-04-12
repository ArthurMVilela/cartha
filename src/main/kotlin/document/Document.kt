package document

import kotlinx.serialization.Serializable

/**
 * Representa um documento.
 *
 * @property id         identificado único
 * @property status     estado do documento
 * @property official   Oficial que autorizou e criou documento
 * @property notary     Cartório em que o documento foi criado
 */
@Serializable
abstract class Document() {
    abstract val id: String?
    abstract val status: DocumentStatus
    abstract val official: Official
    abstract val notary: Notary
}