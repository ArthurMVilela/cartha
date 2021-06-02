package authentication.exception

class UserOfflineException(override val message: String?="Usuário offline."):AuthenticationException(message) {
}