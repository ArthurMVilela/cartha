package authentication.handlers

import authentication.Role
import authentication.controllers.AuthenticationController
import authentication.exception.InvalidCredentialsException
import authentication.exception.InvalidPasswordException
import authentication.exception.UserSessionNotFound
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

    /**
     * Recebe uma chamada de API e inicia uma sessão de usuário
     *
     * @param call          Chamada de API
     */
    suspend fun login(call: ApplicationCall) {
        val parameters = call.receiveParameters()
        val email = parameters["email"]
        val cpf = parameters["cpf"]
        val cnpj = parameters["cnpj"]
        val password = parameters["password"]?:throw BadRequestException("Senha do usuário deve ser expecificads na requisição.")

        val session = try {
            controller.login(email, cpf, cnpj, password)
        } catch (ex: InvalidCredentialsException) {
            throw BadRequestException(ex.message?:"Credentiais de usuário inválidas.")
        } catch (ex: InvalidPasswordException) {
            throw BadRequestException("Credentiais de usuário inválidas.")
        }

        call.respond(HttpStatusCode.OK, session)
    }

    suspend fun getSession(call: ApplicationCall) {
        val id = call.parameters["id"]?:throw BadRequestException("Id não pode ser nula.")
        val session = try {
            controller.getSession(UUID.fromString(id))
        } catch (ex: UserSessionNotFound) {
            throw NotFoundException("Sessão de usuário não encontrada.")
        } catch (ex: Exception) {
            throw ex
        }
        call.respond(HttpStatusCode.OK, session)
    }

    suspend fun logout(call: ApplicationCall) {
        val id = call.parameters["id"]?:throw BadRequestException("Id não pode ser nula.")
        val logoutSession = try {
            controller.logout(UUID.fromString(id))
        } catch (ex:Exception) {
            throw ex
        }
        call.respond(HttpStatusCode.OK, logoutSession)
    }

    suspend fun getAccount(call: ApplicationCall) {
        val email = call.parameters["email"]?:throw BadRequestException("Email não pode ser nulo.")
        val user = try {
            controller.getUserAccount(email)
        } catch (ex:Exception) {
            throw ex
        }
        call.respond(HttpStatusCode.OK,user)
    }

    suspend fun getAccountByCpf(call: ApplicationCall) {
        val cpf = call.parameters["cpf"]?:throw BadRequestException("CPF não pode ser nulo.")
        val user = try {
            controller.getUserAccountByCpf(cpf)
        } catch (ex:Exception) {
            throw ex
        }
        call.respond(HttpStatusCode.OK,user)
    }
}