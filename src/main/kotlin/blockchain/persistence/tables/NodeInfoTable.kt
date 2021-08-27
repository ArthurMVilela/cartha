package blockchain.persistence.tables

import blockchain.NodeStatus
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.`java-time`.datetime

object NodeInfoTable:UUIDTable("node_info") {
    val notaryId = uuid("notary_id")
    val address = varchar("address", 120)
    val status = enumeration("status", NodeStatus::class)
    val lastHealthCheck = datetime("last_health_check").nullable()
}