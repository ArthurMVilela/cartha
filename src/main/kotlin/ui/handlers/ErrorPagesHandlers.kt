package ui.handlers

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.sessions.*
import ui.controllers.AuthenticationController
import ui.exception.AuthenticationFeatureException
import ui.features.UserSessionCookie
import ui.util.Util
import java.lang.Exception

class ErrorPagesHandlers {
    val authController = AuthenticationController()

    /**
     * Recebe uma chamada de aplicação e uma excessão, e retorna ao usuário uma tela informando sobre o erro.
     *
     * @param call          chamada de aplicação
     * @param exception     excessão que causou o erro
     */
    suspend fun handleError(call: ApplicationCall, exception: Exception) {
        val data = mutableMapOf<String, Any?>()
        var code:HttpStatusCode
        when (exception) {
            is AuthenticationFeatureException -> {
                code = HttpStatusCode.Unauthorized
                data["code"] = code.value
                data["errorTitle"] = "Não autorizado."
                data["errorDesc"] = "Você não está autorizado a accessar este recurso."
            }
            is NotFoundException -> {
                code = HttpStatusCode.NotFound
                data["code"] = code.value
                data["errorTitle"] = "Não encontrado."
                data["errorDesc"] = "A pagina que procura não existe."
            }
            else -> {
                code = HttpStatusCode.InternalServerError
                data["code"] = code.value
                data["errorTitle"] = "Erro inesperado no servidor."
                data["errorDesc"] = "Ocorreu um erro inesperado no servidor."
            }
        }
        val role = try {
            authController.getUserRole(call.sessions.get<UserSessionCookie>()!!)
        } catch (ex: Exception) {
            null
        }
        Util.addMenuToLayoutMap(data, role)
        call.respond(code, FreeMarkerContent("error.ftl", data))
    }
}