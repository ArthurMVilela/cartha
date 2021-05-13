package authentication.logging

import java.time.LocalDateTime
import java.util.*
import javax.security.auth.Subject

/**
 * Representa um filtro que será aplicada para uma consulta ao banco de dados
 *
 * @param userId            id de um usuário expecifico
 * @param start             início do periodo em que se quer ver logs de acesso
 * @param end               fim do periodo em que se quer ver logs de acesso
 * @param domainId          id do dominio relacionado aos logs de acesso
 * @param actionType        tipo de ação dos logs de acesso
 * @param subject           Assunto dos logs de acessos
 */
class AccessLogSearchFilter(
    val userId: UUID?,
    val start: LocalDateTime?,
    val end: LocalDateTime?,
    val domainId: UUID?,
    val actionType: ActionType?,
    val subject: Subject?
) {
}