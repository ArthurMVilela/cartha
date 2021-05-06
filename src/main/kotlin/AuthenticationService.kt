@file:JvmName("AuthenticationService")

import authentication.services.AuthenticationService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import kotlin.system.exitProcess

fun main() {

    try {
        val host = "localhost"//System.getenv("DATABASE_HOST")
        val port = "3306"//System.getenv("DATABASE_PORT")
        val database = "authentication_db"//System.getenv("DATABASE_NAME")
        val user = "root"//System.getenv("DATABASE_USER")
        val password = "test"//System.getenv("DATABASE_PASSWORD")
        val url = "jdbc:mysql://$host:$port/$database?verifyServerCertificate=false&useSSL=false"
        println(url)
        val db = Database.connect(
            url = url,
            driver = "com.mysql.jdbc.Driver",
            user = user,
            password = password,
        )

        TransactionManager.defaultDatabase = db
    } catch (e:Exception) {
        println(e.message)
        exitProcess(2)
    }

    val service = AuthenticationService()
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            json()
        }
        routing {
            post("/user/client") {
                service.createClientUser(call)
            }
            post("/user/official") {
                service.createOfficialUser(call)
            }
            post("/user/manager") {
                service.createManagerUser(call)
            }
            post("/user/sysadmin") {
                service.createSysAdmin(call)
            }
            post("/login") {
                service.login(call)
            }
            get("/session/{id}") {
                service.getSession(call)
            }
            post("/logout/{id}") {
                service.logout(call)
            }


        }
    }.start(true)

}