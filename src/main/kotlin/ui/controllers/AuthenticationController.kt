package ui.controllers

import authentication.Role
import authentication.Subject
import authentication.User
import authentication.UserSession
import authentication.handlers.AccessLogRequestBody
import authentication.logging.AccessLog
import authentication.logging.AccessLogSearchFilter
import authentication.logging.Action
import authentication.logging.ActionType
import persistence.ResultSet
import ui.exception.InvalidUserSessionException
import ui.features.UserSessionCookie
import java.time.LocalDateTime
import java.util.*

class AuthenticationController {
    private val authenticationClient = AuthenticationClient()

    /**
     * Efetua o login do usuário e retorna a sessão de usuário iníciada
     *
     * @param email         email do usuário
     * @param cpf           cpf do usuário
     * @param cnpj          cnpj do usuário
     * @param password      senha do usuário
     */
    suspend fun login(email: String?, cpf: String?, cnpj: String?, password: String): UserSession {
        return authenticationClient.login(email, cpf, cnpj, password)
    }

    /**
     * Termina uma sessão de usuário dado um cookie com a id da sessão
     *
     * @param sessionCookie         Cookie com a sessão de usuário
     */
    suspend fun logout(sessionCookie: UserSessionCookie) {
        val id = UUID.fromString(sessionCookie.sessionId)
        authenticationClient.logout(id)
    }

    /**
     * Verifica se a sessão de usuário no cookie é válida
     *
     * @param sessionCookie         cookie com a sessão de usuário
     */
    suspend fun isSessionValid(sessionCookie:UserSessionCookie) : Boolean {
        val id = UUID.fromString(sessionCookie.sessionId)

        val session = try {
            authenticationClient.getSession(UUID.fromString(sessionCookie.sessionId))
        } catch (ex: Exception) {
            throw ex
        }

        return session != null && !session.hasEnded()
    }

    /**
     * Busca uma sessão de usuário dado um cookie de sessão de usuário
     *
     * @param sessionCookie         cookie com a sessão de usuário
     */
    suspend fun getSession(sessionCookie:UserSessionCookie) : UserSession {
        val id = UUID.fromString(sessionCookie.sessionId)

        val session = try {
            authenticationClient.getSession(UUID.fromString(sessionCookie.sessionId))
        } catch (ex: Exception) {
            throw ex
        }?:throw Exception()

        return session
    }

    /**
     * Busca o cargo de um usuário dado o cookie de uma sessão de usuário
     *
     * @param sessionCookie         cookie com a sessão de usuário
     */
    suspend fun getUserRole(sessionCookie:UserSessionCookie) : Role {
        val session = try {
            authenticationClient.getSession(UUID.fromString(sessionCookie.sessionId))
        } catch (ex: Exception) {
            null
        }

        if (session != null && !session.hasEnded()) {
            return session.user.role
        } else {
            throw InvalidUserSessionException("Sessão de usuário inválida.")
        }
    }

    suspend fun getAccessLogs(filter: AccessLogSearchFilter, page: Int = 1):ResultSet<AccessLog> {
        return authenticationClient.getAccessLogs(filter, page)
    }
    suspend fun getAccessLog(id:UUID):AccessLog {
        return authenticationClient.getAccessLog(id)
    }

    /**
     * Registra um log de acesso
     */
    suspend fun logAction(sessionCookie:UserSessionCookie, actionType: ActionType, subject: Subject, domainId: UUID?) {
        val request = AccessLogRequestBody(
            UUID.fromString(sessionCookie.sessionId),
            Action(actionType, subject, domainId),
            LocalDateTime.now()
        )

        authenticationClient.logAction(request)
    }

    suspend fun createAccount(
        name: String,
        role: Role,
        email: String,
        cpf: String?,
        cnpj: String?,
        password: String,
        notaryId: UUID?
    ): User {
        return authenticationClient.createAccount(name, role, email, cpf, cnpj, password, notaryId)
    }

    suspend fun getUserAccount(email: String):User {
        return authenticationClient.getUserAccount(email)
    }
}