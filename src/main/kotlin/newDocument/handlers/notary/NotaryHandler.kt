package newDocument.handlers.notary

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import newDocument.Notary
import newDocument.controllers.NotaryController
import java.util.*

/**
 * Handler para lidar com as rotas relacionadas a cartórios
 */
class NotaryHandler {
    val controller = NotaryController()
    /**
     * Recebe uma chamada de aplicação e registra um novo cartório
     */
    suspend fun createNotary(call: ApplicationCall) {
        val requestBody = try {
            call.receive<CreateNotaryRequest>()
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw BadRequestException("Corpo de requesição inválido")
        }

        val notary = try {
            controller.createNotary(
                Notary(
                    Notary.createId(),
                    requestBody.name,
                    requestBody.cnpj,
                    requestBody.cns
                )
            )
        } catch (ex: Exception) {
            throw ex
        }

        call.respond(HttpStatusCode.Created, notary)
    }
    /**
     * Recebe uma chamada de aplicação e busca um cartório com id ou cnpj específico
     */
    suspend fun getNotary(call: ApplicationCall) {
        val idParameter = call.parameters["id"]
        val cnpjParameter = call.parameters["cnpj"]

        if (idParameter != null) {
            val id = try {
                UUID.fromString(call.parameters["id"])
            } catch (ex: Exception) {
                throw BadRequestException("Id inválida.")
            }

            val notary = try {
                controller.getNotary(id)
            } catch (ex: Exception) {
                throw BadRequestException("")
            }?:throw NotFoundException("Cartório não encontrado.")

            call.respond(HttpStatusCode.OK, notary)
        } else if(cnpjParameter != null) {
            val notary = try {
                controller.getNotary(cnpjParameter)
            } catch (ex: Exception) {
                throw BadRequestException("")
            }?:throw NotFoundException("Cartório não encontrado.")

            call.respond(HttpStatusCode.OK, notary)
        } else {
            throw BadRequestException("")
        }

    }

    suspend fun getNotaries(call: ApplicationCall) {
        val page = try {
            call.request.queryParameters["page"]?.toInt()?:1
        } catch (ex: Exception) {
            throw serviceExceptions.BadRequestException("Página inválida")
        }

        if (page < 1) {
            throw serviceExceptions.BadRequestException("Página inválida")
        }

        val notaries = controller.getNotaries(page)

        call.respond(HttpStatusCode.OK, notaries)
    }
}