package ui.features

import io.ktor.auth.*

class UserSessionCookie(
    val sessionId: String
): Principal {
}