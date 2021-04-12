package document

import kotlinx.serialization.Serializable

/**
 * Representa um documento.
 *
 * @property id             identificado único
 * @property status         estado do documento
 * @property officialId     Id do oficial que autorizou e criou documento
 * @property notaryId       Id do cartório em que o documento foi criado
 */
@Serializable
abstract class Document() {
    abstract val id: String?
    abstract val status: DocumentStatus
    abstract val officialId: String
    abstract val notaryId: String
}