package blockchain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import util.serializer.LocalDateTimeSerializer
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import kotlin.collections.HashMap
import kotlin.random.Random

@Serializable
class Transaction (
    var id:String?,
    @Serializable(with = LocalDateTimeSerializer::class)
    val timestamp:LocalDateTime,
    @SerialName("document_id")
    val documentId:String,
    @SerialName("document_hash")
    val documentHash:String,
    val type:TransactionType,
    var hash:String?
){
    constructor(
        timestamp: LocalDateTime,
        documentId: String,
        documentHash: String,
        type: TransactionType
    ):this(null, timestamp, documentId, documentHash, type, null) {
        this.id = createId()
        this.hash = createHash()
    }

    fun createId(): String {
        val md = MessageDigest.getInstance("SHA")
        val now = LocalDateTime.now(ZoneOffset.UTC)
        var content = now.toString().toByteArray()
        content = content.plus(Random(now.toEpochSecond(ZoneOffset.UTC)).nextBytes(10))
        return Base64.getUrlEncoder().encodeToString(md.digest(content))
    }

    fun createHash():String {
        val md = MessageDigest.getInstance("SHA-256")
        var content = Base64.getUrlDecoder().decode(id)
        content = content.plus(timestamp.toString().toByteArray())
        content = content.plus(documentId.toByteArray())
        content = content.plus(Base64.getUrlDecoder().decode(documentId))
        content = content.plus(Base64.getUrlDecoder().decode(documentHash))
        content = content.plus(type.value.toByteArray())
        return Base64.getUrlEncoder().encodeToString(md.digest(content))
    }
}