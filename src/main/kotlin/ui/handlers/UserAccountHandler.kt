package ui.handlers

import authentication.Role
import authentication.Subject
import authentication.logging.ActionType
import document.handlers.person.CreateOfficialRequest
import document.handlers.person.CreatePhysicalPersonRequest
import document.person.CivilStatus
import document.person.Color
import document.person.Sex
import io.ktor.application.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.sessions.*
import ui.controllers.AuthenticationController
import ui.controllers.DocumentController
import ui.features.UserSessionCookie
import ui.features.getUserRole
import ui.features.logAction
import ui.pages.CreateClientPageBuilder
import ui.pages.CreateManagerPageBuilder
import ui.pages.CreateOfficialPageBuilder
import ui.pages.LoginPageBuilder
import java.time.LocalDate
import java.time.Month
import java.util.*

class UserAccountHandler {
    private val authController = AuthenticationController()
    private val documentController = DocumentController()

    /**
     * Recebe uma chamada da aplicação e retorna a tela de login
     *
     * @param call          chamada de aplicação
     */
    suspend fun loginPage(call: ApplicationCall) {
        val pageBuilder = LoginPageBuilder()
        pageBuilder.setupMenu(call.getUserRole())

        val sessionCookie = call.sessions.get<UserSessionCookie>()
        if (sessionCookie != null) {
            if (authController.isSessionValid(sessionCookie)) {
                call.respondRedirect("/")
                return
            }
        }

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    /**
     * Recebe uma chamada da aplicação e processa o formulário de login
     *
     * @param call          chamada de aplicação
     */
    suspend fun postLogin(call: ApplicationCall) {
        val form = call.receiveParameters()

        val sessionCookie = call.sessions.get<UserSessionCookie>()
        if (sessionCookie != null) {
            if (authController.isSessionValid(sessionCookie)) {
                call.respondRedirect("/")
                return
            }
        }

        try {
            val email = if(form["email"].isNullOrEmpty()) null else form["email"]
            val cpf = if(form["cpf"].isNullOrEmpty()) null else form["cpf"]
            val cnpj = if(form["cnpj"].isNullOrEmpty()) null else form["cnpj"]
            val userSession = authController.login(email, cpf, cnpj, form["password"]!!)
            call.sessions.set(UserSessionCookie(userSession.id.toString()))
            call.respondRedirect("/")
        }  catch (ex: ClientRequestException) {
            val pageBuilder = LoginPageBuilder()
            pageBuilder.setupMenu(call.getUserRole())
            val msg = try {
                ex.response.receive<String>()
            } catch (ex: Exception) {
                "Erro inesperado"
            }
            pageBuilder.setErrorMessage(msg)

            val page = pageBuilder.build()
            call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
        }
        catch (ex: Exception) {
            val pageBuilder = LoginPageBuilder()
            pageBuilder.setupMenu(call.getUserRole())
            pageBuilder.setErrorMessage("Um erro inesperado ocorreu.")

            val page = pageBuilder.build()
            call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
        }
    }

    /**
     * Recebe uma chamada da aplicação e o logout
     *
     * @param call          chamada de aplicação
     */
    suspend fun logout(call: ApplicationCall) {
        val sessionCookie = call.sessions.get<UserSessionCookie>()
        if (sessionCookie != null) {
            if (authController.isSessionValid(sessionCookie)) {
                authController.logout(sessionCookie)
                call.sessions.clear<UserSessionCookie>()
                call.respondRedirect("/")
                return
            }
        }

        call.respondRedirect("/")
    }

    fun userAccountPage(call: ApplicationCall) {
        TODO()
    }

    suspend fun getCreateOfficialPage(call: ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw io.ktor.features.BadRequestException("Id inválida.")
        }

        val pageBuilder = CreateOfficialPageBuilder()

        pageBuilder.setupMenu(call.getUserRole())
        pageBuilder.setNotaryId(id)

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    suspend fun getCreateManagerPage(call: ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw io.ktor.features.BadRequestException("Id inválida.")
        }

        val pageBuilder = CreateManagerPageBuilder()

        pageBuilder.setupMenu(call.getUserRole())
        pageBuilder.setNotaryId(id)

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    suspend fun createOfficial(call: ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw io.ktor.features.BadRequestException("Id inválida.")
        }

        val form = call.receiveParameters()

        if (isRegisteredUser(form["email"]!!, form["cpf"]!!) || isRegisterOfficial(form["cpf"]!!)) {
            generateOfficialErrorPage(call, "Email e/ou CPF já cadastrado", id)
            return
        }

        val user = try {
            authController.createAccount(
                form["name"]!!,
                Role.Official,
                form["email"]!!,
                form["cpf"]!!,
                null,
                form["password"]!!,
                id
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            generateOfficialErrorPage(call, "Occorreu um error inesperado ao tentar criar usuário", id)
            return
        }

        val official = try {
            documentController.createOfficial(CreateOfficialRequest(
                user.id,
                user.name,
                user.cpf!!,
                Sex.valueOf(form["sex"]!!),
                id
            ))
        } catch (ex: Exception) {
            ex.printStackTrace()
            generateOfficialErrorPage(call, "Occorreu um error inesperado ao tentar criar usuário", id)
            return
        }

        call.logAction(ActionType.CreateAccount, Subject.UserAccount, user.id)
        call.logAction(ActionType.AddOfficialToNotary, Subject.Notary, id)

        call.respondRedirect("/notary/$id")
    }

    suspend fun createManager(call: ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw io.ktor.features.BadRequestException("Id inválida.")
        }

        val form = call.receiveParameters()

        if (isRegisteredUser(form["email"]!!, form["cpf"]!!) || isRegisterOfficial(form["cpf"]!!)) {
            generateManagerErrorPage(call, "Email e/ou CPF já cadastrado", id)
            return
        }

        val user = try {
            authController.createAccount(
                form["name"]!!,
                Role.Manager,
                form["email"]!!,
                form["cpf"]!!,
                null,
                form["password"]!!,
                id
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            generateManagerErrorPage(call, "Occorreu um error inesperado ao tentar criar usuário", id)
            return
        }

        val official = try {
            documentController.createOfficial(CreateOfficialRequest(
                user.id,
                user.name,
                user.cpf!!,
                Sex.valueOf(form["sex"]!!),
                id
            ))
        } catch (ex: Exception) {
            ex.printStackTrace()
            generateManagerErrorPage(call, "Occorreu um error inesperado ao tentar criar usuário", id)
            return
        }

        call.logAction(ActionType.CreateAccount, Subject.UserAccount, user.id)
        call.logAction(ActionType.AddManagerToNotary, Subject.Notary, id)

        call.respondRedirect("/notary/$id")
    }

    suspend fun getCreateClientPage(call: ApplicationCall) {
        val pageBuilder = CreateClientPageBuilder()

        pageBuilder.setupMenu(call.getUserRole())

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    suspend fun createClient(call: ApplicationCall){
        val form = call.receiveParameters()

        if (isRegisteredUser(form["email"]!!, form["cpf"]!!) || isRegisterPhysicalPerson(form["cpf"]!!)) {
            generateClientErrorPage(call, "Email e/ou CPF já cadastrado")
            return
        }

        val user = try {
            authController.createAccount(
                form["name"]!!,
                Role.Client,
                form["email"]!!,
                form["cpf"]!!,
                null,
                form["password"]!!,
                null
            )
        } catch (ex: ClientRequestException) {
            val msg = try {
                ex.response.receive<String>()
            } catch (ex: Exception) {
                "Um erro inesperado ocorreu."
            }
            generateClientErrorPage(call, msg)
            return
        } catch (ex: Exception) {
            generateClientErrorPage(call, "Erro ao tentar criar conta de usuário.")
            return
        }

        val physicalPerson = try {
            documentController.createPhysicalPerson(CreatePhysicalPersonRequest(
                user.id,
                user.name,
                user.cpf!!,
                LocalDate.of(
                    form["birthday-year"]!!.toInt(),
                    Month.valueOf(form["birthday-month"]!!),
                    form["birthday-day"]!!.toInt()
                ),
                Sex.valueOf(form["sex"]!!),
                Color.valueOf(form["color"]!!),
                CivilStatus.valueOf(form["civil-status"]!!),
                form["nationality"]!!
            ))
        } catch (ex: Exception) {
            generateClientErrorPage(call, "Erro ao tentar criar conta de usuário.")
            return
        }

        call.respondRedirect("/login")
    }

    private suspend fun isRegisteredUser(email: String, cpf: String): Boolean {
        val registeredUser = try {
            authController.getUserAccount(email)
        } catch (ex: Exception) {
            try {
                authController.getUserAccountByCpf(cpf)
            } catch (ex: Exception) {
                null
            }
        }

        return registeredUser != null
    }

    private suspend fun isRegisterPhysicalPerson(cpf: String): Boolean {
        val registeredPhysicalPerson = try {
            documentController.getPhysicalPerson(cpf)
        } catch (ex: Exception) {
            null
        }

        return registeredPhysicalPerson != null
    }

    private suspend fun isRegisterOfficial(cpf: String): Boolean {
        val registeredOfficial = try {
            documentController.getOfficial(cpf)
        } catch (ex: Exception) {
            null
        }

        return registeredOfficial != null
    }

    private suspend fun generateClientErrorPage(call: ApplicationCall, errorMessage: String) {
        val pageBuilder = CreateClientPageBuilder()

        pageBuilder.setupMenu(call.getUserRole())
        pageBuilder.setErrorMessage(errorMessage)

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    private suspend fun generateOfficialErrorPage(call: ApplicationCall, errorMessage: String, notaryId: UUID) {
        val pageBuilder = CreateOfficialPageBuilder()

        pageBuilder.setupMenu(call.getUserRole())
        pageBuilder.setErrorMessage(errorMessage)
        pageBuilder.setNotaryId(notaryId)

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    private suspend fun generateManagerErrorPage(call: ApplicationCall, errorMessage: String, notaryId: UUID) {
        val pageBuilder = CreateManagerPageBuilder()

        pageBuilder.setupMenu(call.getUserRole())
        pageBuilder.setErrorMessage(errorMessage)
        pageBuilder.setNotaryId(notaryId)

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }


}