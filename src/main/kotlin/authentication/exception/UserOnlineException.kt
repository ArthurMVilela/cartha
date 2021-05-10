package authentication.exception

class UserOnlineException(override val message: String?="Usuário está online."):AuthenticationException(message) {
}