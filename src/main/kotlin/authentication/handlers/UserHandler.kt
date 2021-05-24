package authentication.handlers

import authentication.Role
import authentication.controllers.AuthenticationController
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import java.lang.Exception
import java.lang.NullPointerException
import java.util.*

/**
 * Esta classe oferece funcionalidades para receber e responder chamadas à API de autentificação
 * relacionadas á usuário e sessões de usuários
 */
class UserHandler {
    private val controller = AuthenticationController()

    /**
     * Recebe uma chamada de API e criar um usuário
     *
     * @param call          Chamada de API
     */
    suspend fun createUser(call: ApplicationCall) {
        val parameters = call.receiveParameters()
        val role = try {
            Role.valueOf(parameters["role"]!!)
        } catch (ex: NullPointerException) {
            throw BadRequestException("Cargo do usuário deve ser expecificado na requisição.")
        } catch (ex: IllegalArgumentException) {
            throw BadRequestException("Cargo inválido.")
        }
        val name = parameters["name"]?:throw BadRequestException("Nome do usuário deve ser expecificado na requisição.")
        val email = parameters["email"]?:throw BadRequestException("Email do usuário deve ser expecificado na requisição.")
        val cpf = parameters["cpf"]
        val cnpj = parameters["cnpj"]

        if(cpf.isNullOrEmpty() && cnpj.isNullOrEmpty() || !cpf.isNullOrEmpty() && !cnpj.isNullOrEmpty()) {
            throw BadRequestException("Usuário deve ter cpf ou cnpj definido, e somente um dos dois.")
        }

        val password = parameters["password"]?:throw BadRequestException("Senha do usuário deve ser expecificado na requisiçãol")
        val notaryId = try {
            UUID.fromString(parameters["notary_id"]!!)
        } catch (ex: NullPointerException) {
            if (role != Role.Official && role != Role.Manager) {
                null
            } else {
                throw BadRequestException("Para cargo de ${role.value} se deve expecificar uma ID de cartório.")
            }
        }

        val user = try {
            controller.createUser(role, name, email,  password, cpf, cnpj, notaryId)
        } catch (ex: NullPointerException) {
            throw BadRequestException(ex.message?:"Erro ao criar usuário, parametros inválidos.")
        }

        call.respond(HttpStatusCode.OK, user)
    }
}