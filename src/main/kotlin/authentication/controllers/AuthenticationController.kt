package authentication.controllers

import authentication.Role
import authentication.Subject
import authentication.User
import authentication.UserSession
import authentication.exception.AuthenticationException
import authentication.exception.UserSessionNotFound
import authentication.logging.AccessLog
import authentication.logging.AccessLogSearchFilter
import authentication.logging.Action
import authentication.logging.ActionType
import authentication.logging.controllers.AccessLogController
import persistence.ResultSet
import java.time.LocalDateTime
import java.util.*
import kotlin.Exception

/**
 * Façade para os controles do pacore de autentificação
 */
class AuthenticationController {
    private val accessLogController = AccessLogController()
    private val userController = UserController()

    /**
     * Cria uma conta de usuário
     *
     * @param role              Função/Cargo do usuário
     * @param name              Nome do usuário
     * @param email             Email do usuário
     * @param password          Senha da conta de usuário
     * @param cpf               CPF do usuário
     * @param cnpj              CNPJ do usuário
     * @param notaryId          Id do cartório
     */
    fun createUser(role: Role, name:String, email:String, password:String, cpf:String?, cnpj:String?, notaryId: UUID?): User {
        return when(role) {
            Role.Client -> userController.createClientUser(name, email, cpf, cnpj, password)
            Role.SysAdmin -> userController.createSysAdminUser(name, email, cpf, password)
            Role.Official -> {
                if (notaryId == null) {
                    throw NullPointerException("Id do cartório não pode ser nulo para criar uma conta de oficial.")
                } else {
                    userController.createOfficialUser(name, email, cpf, password, notaryId)
                }
            }
            Role.Manager -> {
                if (notaryId == null) {
                    throw NullPointerException("Id do cartório não pode ser nulo para criar uma conta de oficial.")
                } else {
                    userController.createManagerUser(name, email, cpf, password, notaryId)
                }
            }
        }
    }

    /**
     * Inicia uma sessão de usuário, usuário pode logar com email ou cpf/cnpj, e senha.
     *
     * @param email             Email do usuário
     * @param password          Senha da conta de usuário
     * @param cpf               CPF do usuário
     * @param cnpj              CNPJ do usuário@param email
     *
     * @return sessão de usuário iniciada
     */
    fun login(email: String?, cpf: String?, cnpj: String?, password: String):UserSession {
        var session:UserSession? = null
        try {
            session = userController.login(email, cpf, cnpj, password)
            accessLogController.logAction(session, Action(ActionType.Login, Subject.UserAccount, session.user.id), LocalDateTime.now())
        } catch (ex: Exception) {
            throw ex
        }
        return session
    }

    /**
     * Termina uma sessão de usuário
     *
     * @param sessionId         ID da sessão de usuário
     *
     * @return a sessão de usuário, terminada
     */
    fun logout(sessionId: UUID):UserSession {
        var endedSession:UserSession? = null
        try {
            endedSession = userController.logout(sessionId)
            accessLogController.logAction(endedSession, Action(ActionType.Logout, Subject.UserAccount, endedSession.user.id), LocalDateTime.now())
        } catch (ex: Exception) {
            throw ex
        }
        return endedSession
    }

    /**
     * Busca por uma sessão de usuário com uma ID específica
     *
     * @param sessionId             ID da sessão de usuário
     *
     * @return sessão de usuário encontrada
     */
    fun getSession(sessionId:UUID):UserSession { return userController.getSession(sessionId) }

    /**
     * Registra um log de acesso
     *
     * @param sessionId                 ID da sessão de usuário
     * @param action                    Ação do log
     * @param timestamp                 instante em que a ação ocorreu
     *
     * @return Log de acesso registrado
     */
    fun logAction(sessionId: UUID, action: Action, timestamp: LocalDateTime): AccessLog {
        val session = try {
            userController.getSession(sessionId)
        } catch (ex: UserSessionNotFound) {
            throw ex
        }

        if (session.hasEnded()) {
            throw AuthenticationException("Sessão já foi terminada.")
        }

        return accessLogController.logAction(session,action,timestamp)
    }

    /**
     * Busca por um log de acesso com uma ID específica
     *
     * @param logId                 ID do log de acesso
     *
     * @return log de acesso encontrado
     */
    fun getAccessLog(logId: UUID):AccessLog {return accessLogController.getLog(logId)}

//    fun getAccessLogs(filter: AccessLogSearchFilter):List<AccessLog> {return accessLogController.getLogs(filter)}

    /**
     * Busca por logs de acesso dado um filtro de busca
     *
     * @param filter                Filtro de busca
     * @param page                  Número da pagina da busca (Paginação)
     *
     * @return resultado da busca: pagina com os resutados da busca.
     */
    fun getAccessLogs(filter: AccessLogSearchFilter, page:Int): ResultSet<AccessLog> {return accessLogController.getLogs(filter, page)}
}