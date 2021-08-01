package ui.controllers

import authentication.Role
import authentication.UserSession
import io.ktor.response.*
import ui.features.UserSessionCookie
import java.util.*

class AuthenticationController {
    private val authenticationClient = AuthenticationClient()

    suspend fun login(email: String?, cpf: String?, cnpj: String?, password: String): UserSession {
        return authenticationClient.login(email, cpf, cnpj, password)
    }

    suspend fun logout(sessionCookie: UserSessionCookie) {
        val id = UUID.fromString(sessionCookie.sessionId)

        println(authenticationClient.logout(id))
    }

    suspend fun isSessionValid(sessionCookie:UserSessionCookie) : Boolean {
        val id = UUID.fromString(sessionCookie.sessionId)

        val session = try {
            authenticationClient.getSession(UUID.fromString(sessionCookie.sessionId))
        } catch (ex: Exception) {
            throw ex
        }

        return session != null && !session.hasEnded()
    }

    suspend fun getUserRole(sessionCookie:UserSessionCookie) : Role {
        val id = UUID.fromString(sessionCookie.sessionId)

        val session = try {
            authenticationClient.getSession(UUID.fromString(sessionCookie.sessionId))
        } catch (ex: Exception) {
            null
        }

        if (session != null && !session.hasEnded()) {
            return session.user.role
        } else {
            throw Exception()
        }
    }
}