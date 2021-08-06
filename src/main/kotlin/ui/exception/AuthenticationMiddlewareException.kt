package ui.exception

/**
 * Representa uma excessão ocorrida na feature/middleware de autentificação
 */
open class AuthenticationMiddlewareException(override val message: String? = "Sessão de usuário inválida"):Exception(message) {
}