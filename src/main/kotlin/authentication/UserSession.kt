package authentication

import authentication.exception.UserDeactivatedException
import authentication.exception.UserOfflineException
import authentication.exception.UserOnlineException
import kotlinx.serialization.Serializable
import util.serializer.LocalDateTimeSerializer
import util.serializer.UUIDSerializer
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import kotlin.random.Random

/**
 * Representa uma sessão de usuário
 *
 * @param id            identificador unico da sessão
 * @param user          usuário que relaciona à sessão
 * @param start         começo da sessão
 * @param end           fim da sessão
 */
@Serializable
class UserSession(
    @Serializable(with = UUIDSerializer::class)
    var id:UUID?,
    val user: User,
    @Serializable(with = LocalDateTimeSerializer::class)
    val start: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    var end: LocalDateTime?
) {
    constructor(user: User, start:LocalDateTime):this(null, user, start, null){
        try {
            user.login()
        } catch (ex: UserOnlineException) {
            throw UserOnlineException("Usuário já logado")
        } catch (ex: UserDeactivatedException) {
            throw UserDeactivatedException("Conta de usuário desativada")
        }

        id = createId()
    }

    /**
     * Retorna se a sessão já terminou
     */
    fun hasEnded():Boolean {
        return end != null
    }

    /**
     * Termina a sessão
     *
     * @param time      hora e data que a sessão terminou
     */
    fun endSession(time: LocalDateTime) {
        if (hasEnded()) {
            throw UserOfflineException("Sessão já foi terminada.")
        }

        try {
            user.logout()
        } catch (ex: UserOfflineException) {
            throw UserOfflineException("Usuário já offline.")
        } catch (ex: UserDeactivatedException) {
            throw UserDeactivatedException("Conta de usuário desativada.")
        }

        end = time
    }

    private fun createId():UUID {
        return UUID.randomUUID()
    }
}