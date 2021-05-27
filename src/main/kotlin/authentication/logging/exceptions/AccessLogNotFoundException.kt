package authentication.logging.exceptions

import authentication.exception.AuthenticationException

class AccessLogNotFoundException(override val message: String?="Log de acesso não encontrado."):AuthenticationException(message) {
}