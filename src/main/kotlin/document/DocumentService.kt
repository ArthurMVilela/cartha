package document

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*

class DocumentService {
    val controller = DocumentController()

    suspend fun createPerson(call: ApplicationCall) {
        val person = call.receive<Person>()
        val created = controller.createPerson(person)
        call.respond(created)
    }

    suspend fun getPerson(call: ApplicationCall) {
        val id = call.parameters["id"]
        call.respond(controller.getPerson(id!!))
    }
}