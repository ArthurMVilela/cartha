package newDocument.handlers.person

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import newDocument.controllers.PersonController
import newDocument.person.Official
import newDocument.person.Person
import newDocument.person.PhysicalPerson
import java.lang.Exception
import java.util.*

/**
 * Handler para lidar com as rotas relacionadas a pessoas
 */
class PersonHandler {
    val controller = PersonController()

    /**
     * Recebe uma chamada de aplicação e registra uma nova pessoa física
     */
    suspend fun createPhysicalPerson(call: ApplicationCall) {
        val requestBody = try {
            call.receive<CreatePhysicalPersonRequest>()
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw BadRequestException("Corpo de requesição inválido")
        }

        val p = try {
            controller.createPhysicalPerson(PhysicalPerson(
                Person.createId(),
                requestBody.accountId,
                requestBody.name,
                requestBody.cpf,
                requestBody.birthday,
                requestBody.sex,
                requestBody.color,
                requestBody.civilStatus,
                requestBody.nationality
            ))
        } catch (ex: Exception) {
            throw ex
        }

        call.respond(HttpStatusCode.OK, p)
    }
    /**
     * Recebe uma chamada de aplicação e busca por uma pessoa física com id ou cpf expecifico
     */
    suspend fun getPhysicalPerson(call: ApplicationCall) {
        val idParameter = call.parameters["id"]
        val cpfParameter = call.parameters["cpf"]

        if (idParameter != null) {
            val id = try {
                UUID.fromString(call.parameters["id"])
            } catch (ex: Exception) {
                throw BadRequestException("Id inválida.")
            }

            val p = try {
                controller.getPhysicalPerson(id)
            } catch (ex: Exception) {
                throw BadRequestException("")
            }?:throw NotFoundException("Cartório não encontrado.")

            call.respond(HttpStatusCode.OK, p)
        } else if(cpfParameter != null) {
            val p = try {
                controller.getPhysicalPerson(cpfParameter)
            } catch (ex: Exception) {
                throw BadRequestException("")
            }?:throw NotFoundException("Cartório não encontrado.")

            call.respond(HttpStatusCode.OK, p)
        } else {
            throw BadRequestException("")
        }
    }

    /**
     * Recebe uma chamada de aplicação e registra um novo funcionário
     */
    suspend fun createOfficial(call: ApplicationCall) {
        val requestBody = try {
            call.receive<CreateOfficialRequest>()
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw BadRequestException("Corpo de requesição inválido")
        }

        val o = try {
            controller.createOfficial(Official(
                Person.createId(),
                requestBody.accountId,
                requestBody.name,
                requestBody.cpf,
                requestBody.sex,
                requestBody.notaryId
            ))
        } catch (ex: Exception) {
            throw ex
        }

        call.respond(HttpStatusCode.OK, o)
    }
    /**
     * Recebe uma chamada de aplicação e busca por um funcionário com id ou cpf expecifico
     */
    suspend fun getOfficial(call: ApplicationCall) {
        val idParameter = call.parameters["id"]
        val cpfParameter = call.parameters["cpf"]

        if (idParameter != null) {
            val id = try {
                UUID.fromString(call.parameters["id"])
            } catch (ex: Exception) {
                throw BadRequestException("Id inválida.")
            }

            val o = try {
                controller.getOfficial(id)
            } catch (ex: Exception) {
                throw BadRequestException("")
            }?:throw NotFoundException("Cartório não encontrado.")

            call.respond(HttpStatusCode.OK, o)
        } else if(cpfParameter != null) {
            val o = try {
                controller.getOfficial(cpfParameter)
            } catch (ex: Exception) {
                throw BadRequestException("")
            }?:throw NotFoundException("Cartório não encontrado.")

            call.respond(HttpStatusCode.OK, o)
        } else {
            throw BadRequestException("")
        }
    }
}