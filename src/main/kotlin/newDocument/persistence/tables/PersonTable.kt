package newDocument.persistence.tables

import org.jetbrains.exposed.dao.id.UUIDTable

object PersonTable:UUIDTable("person") {
    val name = varchar("name", 140)
    val accountId = uuid("account_id").nullable()
}