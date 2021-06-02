package authentication.exception

class InvalidCredentialsException(override val message: String? = "Credenciais inválidas."):AuthenticationException(message) {
}