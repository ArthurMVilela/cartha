import document.DocumentService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    val service = DocumentService()
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation)  {
            json()
        }
        routing {

        }
    }.start(true)
}