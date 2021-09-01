package blockchain.persistence.tables

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.`java-time`.datetime

object BlockTable:UUIDTable("block") {
    val timestamp = datetime("timestamp")
    val transactionsHash = char("transactions_hash", 44)
    val previousHash = char("previous_hash", 44)
    val hash = char("hash", 44)
    val nodeId = uuid("node_id")
}