package document

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import util.serializer.UUIDSerializer
import java.util.*

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
    @Serializable(with = UUIDSerializer::class)
    abstract val id: UUID
    abstract var status: DocumentStatus
    @SerialName("official_id")
    abstract val officialId: String
    @SerialName("notary_id")
    abstract val notaryId: String
    abstract var hash: String?

    companion object {
        fun createId():UUID {
            return UUID.randomUUID()
        }
    }

    /**
     * Cria a hash (SHA-256) do documento
     */
    abstract fun createHash():String
}