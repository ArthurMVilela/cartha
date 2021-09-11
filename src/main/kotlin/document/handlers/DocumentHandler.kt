package document.handlers

import document.Notary
import document.Official
import document.PhysicalPerson
import document.civilRegistry.DeathCertificate
import document.controllers.ControllersFacade
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import serviceExceptions.BadRequestException

class DocumentHandler {
    val controller = ControllersFacade()

    suspend fun createPhysicalPerson(call: ApplicationCall) {
        val person: PhysicalPerson
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
        val person: PhysicalPerson
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

    suspend fun createOfficial(call: ApplicationCall) {
        val person: Official
        try {
            person = call.receive<Official>()
        } catch (e:ContentTransformationException) {
            throw BadRequestException("conteudo da requisição é inválido")
        }

        val inserted = controller.createOfficial(person)
        call.respond(HttpStatusCode.Created, inserted)
    }

    suspend fun getOfficial(call: ApplicationCall) {
        val id = call.parameters["id"]
        if (id.isNullOrBlank()) {
            throw BadRequestException("id não pode ser nula ou vázia")
        }
        val found = controller.getOfficial(id)
        if (found == null) {
            call.respond(HttpStatusCode.NotFound, "não encontrado")
            return
        }
        call.respond(HttpStatusCode.OK, found)
    }

    suspend fun updateOfficial(call: ApplicationCall){
        val id = call.parameters["id"]
        if (id.isNullOrBlank()) {
            throw BadRequestException("id não pode ser nula ou vázia")
        }
        val person: Official
        try {
            person = call.receive<Official>()
        } catch (e:ContentTransformationException) {
            throw BadRequestException("conteudo da requisição é inválido")
        }
        val updated = controller.updateOfficial(id, person)
        call.respond(HttpStatusCode.OK, updated)
    }

    suspend fun deleteOfficial(call: ApplicationCall) {
        val id = call.parameters["id"]
        if (id.isNullOrBlank()) {
            throw BadRequestException("id não pode ser nula ou vázia")
        }
        controller.deleteOfficial(id)
        call.respond(HttpStatusCode.OK, "Deletado com sucesso")
    }

    suspend fun createNotary(call: ApplicationCall) {
        val notary: Notary
        try {
            notary = call.receive<Notary>()
        } catch (e:ContentTransformationException) {
            throw BadRequestException("conteudo da requisição é inválido")
        }

        val inserted = controller.createNotary(notary)
        call.respond(HttpStatusCode.Created, inserted)
    }

    suspend fun getNotary(call: ApplicationCall) {
        val id = call.parameters["id"]
        if (id.isNullOrBlank()) {
            throw BadRequestException("id não pode ser nula ou vázia")
        }
        val found = controller.getNotary(id)
        if (found == null) {
            call.respond(HttpStatusCode.NotFound, "não encontrado")
            return
        }
        call.respond(HttpStatusCode.OK, found)
    }

    suspend fun updateNotary(call: ApplicationCall){
        val id = call.parameters["id"]
        if (id.isNullOrBlank()) {
            throw BadRequestException("id não pode ser nula ou vázia")
        }
        val notary: Notary
        try {
            notary = call.receive<Notary>()
        } catch (e:ContentTransformationException) {
            throw BadRequestException("conteudo da requisição é inválido")
        }
        val updated = controller.updateNotary(id, notary)
        call.respond(HttpStatusCode.OK, updated)
    }

    suspend fun deleteNotary(call: ApplicationCall) {
        val id = call.parameters["id"]
        if (id.isNullOrBlank()) {
            throw BadRequestException("id não pode ser nula ou vázia")
        }
        controller.deleteNotary(id)
        call.respond(HttpStatusCode.OK, "Deletado com sucesso")
    }

    suspend fun createDeathCertificate(call: ApplicationCall) {
        val deathCertificate: DeathCertificate
        try {
            deathCertificate = call.receive<DeathCertificate>()
        } catch (e:ContentTransformationException) {
            throw BadRequestException("conteudo da requisição é inválido")
        }

        val inserted = controller.createDeathCertificate(deathCertificate)
        call.respond(HttpStatusCode.Created, inserted)
    }

    suspend fun getDeathCertificate(call: ApplicationCall) {
        val id = call.parameters["id"]
        if (id.isNullOrBlank()) {
            throw BadRequestException("id não pode ser nula ou vázia")
        }
        val found = controller.getDeathCertificate(id)
        if (found == null) {
            call.respond(HttpStatusCode.NotFound, "não encontrado")
            return
        }
        call.respond(HttpStatusCode.OK, found)
    }

    suspend fun updateDeathCertificate(call: ApplicationCall){
        val id = call.parameters["id"]
        if (id.isNullOrBlank()) {
            throw BadRequestException("id não pode ser nula ou vázia")
        }
        val deathCertificate: DeathCertificate
        try {
            deathCertificate = call.receive<DeathCertificate>()
        } catch (e:ContentTransformationException) {
            throw BadRequestException("conteudo da requisição é inválido")
        }
        val updated = controller.updateDeathCertificate(id, deathCertificate)
        call.respond(HttpStatusCode.OK, updated)
    }

    suspend fun deleteDeathCertificate(call: ApplicationCall) {
        val id = call.parameters["id"]
        if (id.isNullOrBlank()) {
            throw BadRequestException("id não pode ser nula ou vázia")
        }
        controller.deleteDeathCertificate(id)
        call.respond(HttpStatusCode.OK, "Deletado com sucesso")
    }
}