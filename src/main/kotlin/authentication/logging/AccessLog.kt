package authentication.logging

import authentication.UserSession
import authentication.exception.UserOfflineException
import kotlinx.serialization.Serializable
import util.serializer.LocalDateTimeSerializer
import util.serializer.UUIDSerializer
import java.time.LocalDateTime
import java.util.*

/**
 * Representa um log de acesso
 *
 * @param id            identificador único do log
 * @param sessionId     id da sessão em que a ação ocorreu
 * @param userId        id do usuário que executou a ação
 * @param timestamp     instante em que a ação ocorreu
 * @param action        ação que se refere o log
 */
@Serializable
class AccessLog (
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @Serializable(with = UUIDSerializer::class)
    val sessionId: UUID,
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    @Serializable(with = LocalDateTimeSerializer::class)
    val timestamp: LocalDateTime,
    val action: Action
){
    constructor(
        session: UserSession,
        action: Action,
        timestamp: LocalDateTime
    ):this(
        createId(),
        session.id,
        session.user.id,
        timestamp,
        action
    ) {
//        if (session.hasEnded()) {
//            throw UserOfflineException("A sessão já foi terminada.")
//        }
    }

    companion object {
        /**
         * Cria o identificador único para este log de acesso
         *
         * @return UUID para a id do log de acesso
         */
        private fun createId():UUID {
            return UUID.randomUUID()
        }

    }
}