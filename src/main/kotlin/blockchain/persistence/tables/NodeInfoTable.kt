package blockchain.persistence.tables

import blockchain.NodeStatus
import org.jetbrains.exposed.dao.id.UUIDTable

object NodeInfoTable:UUIDTable("node_info") {
    val notaryId = uuid("notary_id")
    val address = varchar("address", 120)
    val status = enumeration("status", NodeStatus::class)
}