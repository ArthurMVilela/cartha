package document

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.ZoneOffset
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
    abstract val id: String?
    abstract val status: DocumentStatus
    @SerialName("official_id")
    abstract val officialId: String
    @SerialName("notary_id")
    abstract val notaryId: String

    fun createId(): String {
        val md = MessageDigest.getInstance("SHA-256")
        val now = LocalDateTime.now(ZoneOffset.UTC)
        return Base64.getUrlEncoder().encodeToString(md.digest(now.toString().toByteArray()))
    }
}