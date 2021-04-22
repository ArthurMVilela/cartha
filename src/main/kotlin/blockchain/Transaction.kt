package blockchain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import util.serializer.LocalDateTimeSerializer
import java.security.MessageDigest
import java.time.LocalDateTime
import java.util.*

@Serializable
class Transaction (
    var id:String?,
    @Serializable(with = LocalDateTimeSerializer::class)
    val timestamp:LocalDateTime,
    @SerialName("document_id")
    val documentId:String,
    @SerialName("document_hash")
    val documentHash:String,
    val type:TransactionType
){
    fun createHash():String {
        val md = MessageDigest.getInstance("SHA-256")
        val content = Json.encodeToString(this).toByteArray()
        return Base64.getUrlEncoder().encodeToString(md.digest(content))
    }
}