package document

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*

class DocumentService {
    val controller = DocumentController()

    suspend fun createPhysicalPerson(call: ApplicationCall) {
        val person = call.receive<PhysicalPerson>()
        val registeredPerson = controller.personController.createPhysicalPerson(person)
        call.respond(HttpStatusCode.Created, registeredPerson)
    }
}