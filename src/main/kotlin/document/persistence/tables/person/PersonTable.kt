package document.persistence.tables.person

import org.jetbrains.exposed.dao.id.UUIDTable

object PersonTable:UUIDTable("person") {
    val name = varchar("name", 140)
    val accountId = uuid("account_id").nullable()
}