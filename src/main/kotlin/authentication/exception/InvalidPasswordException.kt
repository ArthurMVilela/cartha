package authentication.exception

class InvalidPasswordException(override val message: String? = "Senha inválida."):AuthenticationException(message) {
}