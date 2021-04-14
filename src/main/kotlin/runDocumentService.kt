import document.DocumentService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager

fun main() {
    val db = Database.connect(
        url = "jdbc:mysql://localhost:3306/cartha_document_db",
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
        routing {
            get("/person/physical_person/{id}") {
                service.getPhysicalPerson(call)
            }
            patch("/person/physical_person/{id}") {
                service.updatePhysicalPerson(call)
            }
            post("/person/physical_person") {
                service.createPhysicalPerson(call)
            }
        }
    }.start(true)
}