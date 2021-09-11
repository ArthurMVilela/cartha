package ui.handlers

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.sessions.*
import ui.controllers.AuthenticationController
import ui.exception.AuthenticationMiddlewareException
import ui.features.UserSessionCookie
import ui.features.getUserRole
import ui.pages.ErrorPageBuilder
import ui.util.Util
import java.lang.Exception

/**
 * Handler para a página de erro.
 */
class ErrorPageHandler {
    /**
     * Recebe uma chamada de aplicação e uma excessão, e retorna ao usuário uma tela informando sobre o erro.
     *
     * @param call          chamada de aplicação
     * @param exception     excessão que causou o erro
     */
    suspend fun handleError(call: ApplicationCall, exception: Exception) {
        val pageBuilder = ErrorPageBuilder()
        val code:HttpStatusCode
        when (exception) {
            is AuthenticationMiddlewareException -> {
                code = HttpStatusCode.Unauthorized
                pageBuilder.setError(
                    code,
                    "Não autorizado.",
                    "Você não está autorizado a accessar este recurso."
                )
            }
            is NotFoundException -> {
                code = HttpStatusCode.NotFound
                pageBuilder.setError(
                    code,
                    "Não encontrado.",
                    "A pagina que procura não existe."
                )
            }
            else -> {
                code = HttpStatusCode.InternalServerError
                pageBuilder.setError(
                    code,
                    "Erro inesperado no servidor.",
                    "Ocorreu um erro inesperado no servidor."
                )
            }
        }

        pageBuilder.setupMenu(call.getUserRole())

        val page = pageBuilder.build()
        call.respond(code, FreeMarkerContent(page.template, page.data))
    }
}