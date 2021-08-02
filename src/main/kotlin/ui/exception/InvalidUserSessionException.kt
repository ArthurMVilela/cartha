package ui.exception

/**
 *
 */
class InvalidUserSessionException(override val message: String?):AuthenticationFeatureException(message) {
}