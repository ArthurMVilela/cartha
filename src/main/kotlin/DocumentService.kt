@file:JvmName("DocumentService")

import serviceExceptions.BadRequestException
import document.service.DocumentService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
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

    val service = DocumentService()
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation)  {
            json()
        }
        install(StatusPages) {
            exception<BadRequestException> { cause ->
                call.respond(HttpStatusCode.BadRequest, cause.message!!)
            }
        }
        routing {
            get("/person/physical_person/{id}") {
                service.getPhysicalPerson(call)
            }
            post("/person/physical_person") {
                service.createPhysicalPerson(call)
            }
            patch("/person/physical_person/{id}") {
                service.updatePhysicalPerson(call)
            }
            delete("/person/physical_person/{id}") {
                service.deletePhysicalPerson(call)
            }

            get("/person/official/{id}") {
                service.getOfficial(call)
            }
            post("/person/official") {
                service.createOfficial(call)
            }
            patch("/person/official/{id}") {
                service.updateOfficial(call)
            }
            delete("/person/official/{id}") {
                service.deleteOfficial(call)
            }

            get("/notary/{id}") {
                service.getNotary(call)
            }
            post("/notary") {
                service.createNotary(call)
            }
            patch("/notary/{id}") {
                service.updateNotary(call)
            }
            delete("/notary/{id}") {
                service.deleteNotary(call)
            }

            get("/civil_registry/death_certificate/{id}") {
                service.getDeathCertificate(call)
            }
            post("/civil_registry/death_certificate") {
                service.createDeathCertificate(call)
            }
            patch("/civil_registry/death_certificate/{id}") {
                service.updateDeathCertificate(call)
            }
            delete("/civil_registry/death_certificate/{id}") {
                service.deleteDeathCertificate(call)
            }
        }
    }.start(true)
}