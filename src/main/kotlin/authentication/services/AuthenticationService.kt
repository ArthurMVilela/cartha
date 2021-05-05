package authentication.services

import authentication.controllers.AuthenticationController
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*

class AuthenticationService {
    private val controller = AuthenticationController()

    suspend fun createClientUser(call: ApplicationCall) {
        val parameters = call.receiveParameters()
        val name = parameters["name"]
        val email = parameters["email"]
        val cpf = parameters["cpf"]
        val password = parameters["password"]

        val user = controller.createClientUser(name!!, email!!, cpf!!, password!!)

        call.respond(HttpStatusCode.Created, user)
    }

    suspend fun createOfficialUser(call: ApplicationCall) {
        val parameters = call.receiveParameters()
        val name = parameters["name"]
        val email = parameters["email"]
        val cpf = parameters["cpf"]
        val notaryId = parameters["notaryId"]
        val password = parameters["password"]

        val user = controller.createOfficialUser(name!!, email!!, cpf!!, password!!, notaryId!!)

        call.respond(HttpStatusCode.Created, user)
    }
    
    suspend fun createManagerUser(call: ApplicationCall) {
        val parameters = call.receiveParameters()
        val name = parameters["name"]
        val email = parameters["email"]
        val cpf = parameters["cpf"]
        val notaryId = parameters["notaryId"]
        val password = parameters["password"]

        val user = controller.createManagerUser(name!!, email!!, cpf!!, password!!, notaryId!!)

        call.respond(HttpStatusCode.Created, user)
    }

    suspend fun createSysAdmin(call: ApplicationCall) {
        val parameters = call.receiveParameters()
        val name = parameters["name"]
        val email = parameters["email"]
        val cpf = parameters["cpf"]
        val password = parameters["password"]

        val user = controller.createSysAdmin(name!!, email!!, cpf!!, password!!)

        call.respond(HttpStatusCode.Created, user)
    }
}