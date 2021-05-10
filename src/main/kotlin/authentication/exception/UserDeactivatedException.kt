package authentication.exception

class UserDeactivatedException(override val message: String?="Conta de usuário desativada"):AuthenticationException(message) {
}