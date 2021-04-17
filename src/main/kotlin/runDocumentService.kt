import document.BadRequestException
import document.DocumentService
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
    val db = Database.connect(
        url = "jdbc:mysql://localhost:3306/cartha_document_db?verifyServerCertificate=false&useSSL=true",
        driver = "com.mysql.jdbc.Driver",
        user = "root",
        password = "test"
    )
    TransactionManager.defaultDatabase = db

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
        }
    }.start(true)
}