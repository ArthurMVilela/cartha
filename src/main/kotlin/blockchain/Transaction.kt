package blockchain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import util.serializer.LocalDateTimeSerializer
import util.serializer.UUIDSerializer
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import kotlin.collections.HashMap
import kotlin.random.Random

@Serializable
class Transaction (
    @Serializable(with = UUIDSerializer::class)
    val id:UUID,
    @Serializable(with = LocalDateTimeSerializer::class)
    val timestamp:LocalDateTime,
    @Serializable(with = UUIDSerializer::class)
    @SerialName("document_id")
    val documentId:UUID,
    @SerialName("document_hash")
    val documentHash:String,
    val type:TransactionType,
    var hash:String?,
    var pending:Boolean = true,
    @Serializable(with = UUIDSerializer::class)
    var blockId:UUID? = null
){
    constructor(
        timestamp: LocalDateTime,
        documentId: UUID,
        documentHash: String,
        type: TransactionType
    ):this(createId(), timestamp, documentId, documentHash, type, null) {
        this.hash = createHash()
    }

    companion object {
        /**
         * Cria o identificador único para a transação
         *
         * @return UUID para a id do usuário
         */
        private fun createId():UUID {
            return UUID.randomUUID()
        }
    }

    fun createHash():String {
        val md = MessageDigest.getInstance("SHA-256")
        var content = Base64.getUrlDecoder().decode(id.toString().toByteArray())
        content = content.plus(timestamp.toString().toByteArray())
        content = content.plus(documentId.toString().toByteArray())
        content = content.plus(Base64.getUrlDecoder().decode(documentHash))
        content = content.plus(type.value.toByteArray())
        return Base64.getUrlEncoder().encodeToString(md.digest(content))
    }
}