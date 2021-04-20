package blockchain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import util.serializer.LocalDateTimeSerializer
import java.time.LocalDateTime

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
}