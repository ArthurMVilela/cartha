package blockchain.persistence.tables

import blockchain.TransactionType
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.`java-time`.datetime

object TransactionTable:UUIDTable("transaction") {
    val timestamp = datetime("timestamp")
    val documentId = uuid("document_id")
    val documentHash = char("document_hash", 64) //TODO: descobrir tamanho da hash
    val type = enumeration("type", TransactionType::class)
    val hash = char("hash", 64) //TODO: descobrir tamanho da hash
    val pending = bool("pending")
}