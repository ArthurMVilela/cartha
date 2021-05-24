package authentication.exception

class UserSessionNotFound(override val message: String?="Sessão de usuário não encontrada."):AuthenticationException(message) {
}