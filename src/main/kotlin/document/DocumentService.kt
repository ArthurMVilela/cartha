package document

import document.controllers.ControllersFacade
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*

class DocumentService {
    val controller = ControllersFacade()

    suspend fun createPhysicalPerson(call: ApplicationCall) {
        val person = call.receive<PhysicalPerson>()
        val registeredPerson = controller.personController.createPhysicalPerson(person)
        call.respond(HttpStatusCode.Created, registeredPerson)
    }

    suspend fun getPhysicalPerson(call: ApplicationCall) {
        val id = call.parameters["id"]
        if (id != null) {
            val found = controller.personController.getPhysicalPerson(id)
            if (found != null) {
                call.respond(HttpStatusCode.OK, found)
                return
            }
            call.respond(HttpStatusCode.NotFound, "Não encontrado")
        }
        call.respond(HttpStatusCode.BadRequest, "Id não pode ser nula")
    }
}