package blockchain.handlers

import blockchain.TransactionType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import util.serializer.UUIDSerializer
import java.util.*

@Serializable
class CreateTransactionRequest(
    @Serializable(with = UUIDSerializer::class)
    @SerialName("document_id")
    val documentId: UUID,
    @SerialName("document_hash")
    val documentHash: String,
    val type: TransactionType
)