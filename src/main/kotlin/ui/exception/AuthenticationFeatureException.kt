package ui.exception

/**
 * Representa uma excessão ocorrida na feature/middleware de autentificação
 */
class AuthenticationFeatureException(override val message: String?):Exception(message) {
}