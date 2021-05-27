package authentication.logging.controllers

import authentication.UserSession
import authentication.exception.UserOfflineException
import authentication.logging.AccessLog
import authentication.logging.AccessLogSearchFilter
import authentication.logging.Action
import authentication.logging.exceptions.AccessLogNotFoundException
import authentication.logging.persistence.AccessLogTable
import authentication.logging.persistence.ActionTable
import authentication.logging.persistence.dao.AccessLogDAO
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.util.*

/**
 * Classe controle para operações relacionadas a logs de acesso
 */
class AccessLogController {
    private val accessLogDAO = AccessLogDAO()

    init {
        transaction {
            SchemaUtils.create(
                AccessLogTable,
                ActionTable
            )
        }
    }

    /**
     * Cria um log de acesso e o registra no banco de dados
     *
     * @param session       sessão de usuário em que a ação ocorreu
     * @param action        ação efetuada
     * @param timestamp     instante em que ocorreu
     */
    fun logAction(session: UserSession, action: Action, timestamp: LocalDateTime):AccessLog {
        val log = try {
            AccessLog(session, action, timestamp)
        } catch (ex: UserOfflineException) {
            throw UserOfflineException("Sessão inválida, sessão já foi terminada.")
        }

        //registrar na persistência


        return accessLogDAO.insert(log)
    }

    /**
     * Busca um log de acesso no bando de dados
     *
     * @param id        id do log
     */
    fun getLog(id: UUID): AccessLog {
        return accessLogDAO.select(id) ?: throw AccessLogNotFoundException()
    }

    /**
     * Busca logs de acesso no banco de dados aplicando um filtro para a busca
     */
    fun getLogs(filter: AccessLogSearchFilter):List<AccessLog> {
        val logs = accessLogDAO.selectMany(filter.createSearchCondition())
        return logs
    }
}