package authentication.logging

import authentication.Subject
import authentication.logging.persistence.AccessLogTable
import authentication.logging.persistence.ActionTable
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.and
import util.serializer.LocalDateTimeSerializer
import util.serializer.UUIDSerializer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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
@Serializable
class AccessLogSearchFilter(
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID?,
    val start: String?,
    val end: String?,
    @Serializable(with = UUIDSerializer::class)
    val domainId: UUID?,
    val actionType: ActionType?,
    val subject: Subject?
) {
    /**
     * Transforma o filtro em uma condição que pode ser usada numa busca SQL
     *
     * @return condição para busca SQL aplicando o filtro dado
     */
    fun createSearchCondition():Op<Boolean> {
        var condition = Op.build { AccessLogTable.id neq null }
        val dateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")

        if (userId != null) {
            condition = Op.build { condition and (AccessLogTable.userId eq userId) }
        }

        when {
            start != null && end != null -> {
                condition = Op.build { condition and ((AccessLogTable.timestamp.between(LocalDateTime.parse(start, dateTimeFormat), LocalDateTime.parse(end, dateTimeFormat)))) }
            }
            start != null && end == null -> {
                condition = Op.build { condition and (AccessLogTable.timestamp greaterEq LocalDateTime.parse(start, dateTimeFormat)) }
            }
            start == null && end != null -> {
                condition = Op.build { condition and (AccessLogTable.timestamp lessEq LocalDateTime.parse(end, dateTimeFormat)) }
            }
        }

        if (domainId != null) {
            condition = Op.build { condition and (ActionTable.domainId eq domainId) }
        }

        if (actionType != null) {
            condition = Op.build { condition and (ActionTable.type eq actionType) }
        }

        if (subject != null) {
            condition = Op.build { condition and (ActionTable.subject eq subject) }
        }

        return condition
    }
}