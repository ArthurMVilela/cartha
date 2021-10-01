package blockchain.persistence.tables

import blockchain.TransactionType
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.`java-time`.datetime

object TransactionTable:UUIDTable("transaction") {
    val timestamp = datetime("timestamp")
    val documentId = uuid("document_id")
    val documentHash = char("document_hash", 44)
    val type = enumeration("type", TransactionType::class)
    val hash = char("hash", 44)
    val pending = bool("pending")
    val blockId = uuid("block_id").nullable()
}