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
        val registeredPerson = controller.createPhysicalPerson(person)
        call.respond(HttpStatusCode.Created, registeredPerson)
    }

    suspend fun getPhysicalPerson(call: ApplicationCall) {
        val id = call.parameters["id"]
        if (id != null) {
            val found = controller.getPhysicalPerson(id)
            if (found != null) {
                call.respond(HttpStatusCode.OK, found)
                return
            }
            call.respond(HttpStatusCode.NotFound, "Não encontrado")
            return
        }
        call.respond(HttpStatusCode.BadRequest, "Id não pode ser nula")
    }

    suspend fun updatePhysicalPerson(call: ApplicationCall) {
        val id = call.parameters["id"]
        if (id === null) {
            call.respond(HttpStatusCode.BadRequest, "Id não pode ser nula")
            return
        }
        val new = call.receive<PhysicalPerson>()
        controller.updatePhysicalPerson(id, new)
        call.respond(HttpStatusCode.OK, "Pessoa física atualizada")
    }

    suspend fun deletePhysicalPerson(call: ApplicationCall) {
        val id = call.parameters["id"]
        if (id === null) {
            call.respond(HttpStatusCode.BadRequest, "Id não pode ser nula")
            return
        }
        controller.deletePhysicalPerson(id)
        call.respond(HttpStatusCode.OK, "Deletado com sucesso")
    }

    suspend fun createLegalPerson(call: ApplicationCall) {
        val person = call.receive<LegalPerson>()
        val registeredPerson = controller.createLegalPerson(person)
        call.respond(HttpStatusCode.Created, registeredPerson)
    }

    suspend fun getLegalPerson(call: ApplicationCall) {
        val id = call.parameters["id"]
        if (id != null) {
            val found = controller.getLegalPerson(id)
            if (found != null) {
                call.respond(HttpStatusCode.OK, found)
                return
            }
            call.respond(HttpStatusCode.NotFound, "Não encontrado")
            return
        }
        call.respond(HttpStatusCode.BadRequest, "Id não pode ser nula")
    }

    suspend fun updateLegalPerson(call: ApplicationCall) {
        val id = call.parameters["id"]
        if (id === null) {
            call.respond(HttpStatusCode.BadRequest, "Id não pode ser nula")
            return
        }
        val new = call.receive<LegalPerson>()
        controller.updateLegalPerson(id, new)
        call.respond(HttpStatusCode.OK, "Pessoa física atualizada")
    }

    suspend fun deleteLegalPerson(call: ApplicationCall) {
        val id = call.parameters["id"]
        if (id === null) {
            call.respond(HttpStatusCode.BadRequest, "Id não pode ser nula")
            return
        }
        controller.deleteLegalPerson(id)
        call.respond(HttpStatusCode.OK, "Deletado com sucesso")
    }
}