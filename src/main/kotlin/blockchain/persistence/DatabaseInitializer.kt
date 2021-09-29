package blockchain.persistence

import blockchain.persistence.tables.BlockTable
import blockchain.persistence.tables.TransactionTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseInitializer {
    companion object {
        fun loadConfigurations() {
            val host = System.getenv("DATABASE_HOST")
            val port = System.getenv("DATABASE_PORT")
            val database = System.getenv("DATABASE_NAME")
            val user = System.getenv("DATABASE_USER")
            val password = System.getenv("DATABASE_PASSWORD")
            val url = "jdbc:mysql://$host:$port/$database?verifyServerCertificate=false&useSSL=true"
            val db = Database.connect(
                url = url,
                driver = "com.mysql.jdbc.Driver",
                user = user,
                password = password,
            )

            TransactionManager.defaultDatabase = db
        }

        fun initialize() {
            transaction {
                SchemaUtils.create(
                    BlockTable, TransactionTable
                )
            }
        }
    }
}