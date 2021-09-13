@file:JvmName("DocumentService")

package newDocument.service

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager

fun main() {
    try {
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
    } catch (e:Exception) {
        println(e.message)
    }

    embeddedServer(Netty, port = 8080) {
        routing {
            get("/") {
                call.respond("hi")
            }
        }
    }.start(true)
}