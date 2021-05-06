package authentication.services

import authentication.controllers.AuthenticationController
import authentication.persistence.dao.UserSessionDAO
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import java.lang.Exception

class AuthenticationService {
    private val controller = AuthenticationController()

    suspend fun createClientUser(call: ApplicationCall) {
        val parameters = call.receiveParameters()
        val name = parameters["name"]
        val email = parameters["email"]
        val cpf = parameters["cpf"]
        val password = parameters["password"]

        val user = try {
            controller.createClientUser(name!!, email!!, cpf!!, password!!)
        } catch (ex: Exception) {
            throw ex
        }

        call.respond(HttpStatusCode.Created, user)
    }

    suspend fun createOfficialUser(call: ApplicationCall) {
        val parameters = call.receiveParameters()
        val name = parameters["name"]
        val email = parameters["email"]
        val cpf = parameters["cpf"]
        val notaryId = parameters["notaryId"]
        val password = parameters["password"]

        val user = try {
            controller.createOfficialUser(name!!, email!!, cpf!!, password!!, notaryId!!)
        } catch (ex: Exception) {
            throw ex
        }

        call.respond(HttpStatusCode.Created, user)
    }
    
    suspend fun createManagerUser(call: ApplicationCall) {
        val parameters = call.receiveParameters()
        val name = parameters["name"]
        val email = parameters["email"]
        val cpf = parameters["cpf"]
        val notaryId = parameters["notaryId"]
        val password = parameters["password"]

        val user = try {
            controller.createManagerUser(name!!, email!!, cpf!!, password!!, notaryId!!)
        } catch (ex: Exception) {
            throw ex
        }

        call.respond(HttpStatusCode.Created, user)
    }

    suspend fun createSysAdmin(call: ApplicationCall) {
        val parameters = call.receiveParameters()
        val name = parameters["name"]
        val email = parameters["email"]
        val cpf = parameters["cpf"]
        val password = parameters["password"]

        val user = try {
            controller.createSysAdmin(name!!, email!!, cpf!!, password!!)
        } catch (ex: Exception) {
            throw ex
        }

        call.respond(HttpStatusCode.Created, user)
    }

    suspend fun login(call: ApplicationCall) {
        val parameters = call.receiveParameters()
        val email = parameters["email"]
        val cpf = parameters["cpf"]
        val password = parameters["password"]

        val session = try {
            controller.login(email, cpf, password!!)
        } catch (ex: Exception) {
            throw ex
        }

        call.respond(HttpStatusCode.OK, session)
    }

    suspend fun getSession(call: ApplicationCall) {
        val id = call.parameters["id"]?:throw BadRequestException("Id não deve ser nula")

        val session = try {
            controller.getSession(id)
        } catch (ex: Exception) {
            throw ex
        }

        call.respond(HttpStatusCode.OK, session)
    }

    suspend fun logout(call: ApplicationCall) {
        val id = call.parameters["id"]?:throw BadRequestException("Id não deve ser nula")

        try {
            controller.logout(id)
        } catch (ex: Exception) {
            throw ex
        }

        call.respond(HttpStatusCode.OK)

    }
}