package document

import document.controllers.ControllersFacade
import document.controllers.PhysicalPersonController
import document.persistence.dao.PhysicalPersonDAO
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

class DocumentService {
    val controller = ControllersFacade()

    suspend fun createPhysicalPerson(call: ApplicationCall) {
        val person:PhysicalPerson
        try {
             person = call.receive<PhysicalPerson>()
        } catch (e:ContentTransformationException) {
            throw BadRequestException("conteudo da requisição é inválido")
        }

        val inserted = controller.createPhysicalPerson(person)
        call.respond(HttpStatusCode.Created, inserted)
    }

    suspend fun getPhysicalPerson(call: ApplicationCall) {
        val id = call.parameters["id"]
        if (id.isNullOrBlank()) {
            throw BadRequestException("id não pode ser nula ou vázia")
        }
        val found = controller.getPhysicalPerson(id)
        if (found == null) {
            call.respond(HttpStatusCode.NotFound, "não encontrado")
            return
        }
        call.respond(HttpStatusCode.OK, found)
    }

    suspend fun updatePhysicalPerson(call: ApplicationCall){
        val id = call.parameters["id"]
        if (id.isNullOrBlank()) {
            throw BadRequestException("id não pode ser nula ou vázia")
        }
        val person:PhysicalPerson
        try {
            person = call.receive<PhysicalPerson>()
        } catch (e:ContentTransformationException) {
            throw BadRequestException("conteudo da requisição é inválido")
        }
        val updated = controller.updatePhysicalPerson(id, person)
        call.respond(HttpStatusCode.OK, updated)
    }

    suspend fun deletePhysicalPerson(call: ApplicationCall) {
        val id = call.parameters["id"]
        if (id.isNullOrBlank()) {
            throw BadRequestException("id não pode ser nula ou vázia")
        }
        controller.deletePhysicalPerson(id)
        call.respond(HttpStatusCode.OK, "Deletado com sucesso")
    }
}