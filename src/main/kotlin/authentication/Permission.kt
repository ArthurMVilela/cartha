package authentication

import kotlinx.serialization.Serializable
import util.serializer.UUIDSerializer
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import kotlin.random.Random

/**
 * Representa uma permissão para o controle de acesso
 */
@Serializable
class Permission(
    val subject: Subject,
    @Serializable(with = UUIDSerializer::class)
    val domainId: UUID?
) {
    companion object {
        /**
         * Retorna a HashSet de permissões padrão do usuário cliente
         *
         * @param userId        id do usuário
         */
        fun getClientDefaultPermissions(userId: UUID):HashSet<Permission> {
            return hashSetOf(
                Permission(Subject.UserAccount, userId),
                Permission(Subject.PersonalDocument, userId)
            )
        }

        /**
         * Retorna a HashSet de permissões padrão do usuário oficial
         *
         * @param userId        id do usuário
         * @param notaryId      id do cartório
         */
        fun getOfficialDefaultPermissions(userId: UUID, notaryId: UUID): HashSet<Permission> {
            return hashSetOf(
                Permission(Subject.UserAccount, userId),
                Permission(Subject.CivilRegistry, notaryId)
            )
        }

        /**
         * Retorna a HashSet de permissões padrão do usuário gerente
         *
         * @param userId        id do usuário
         * @param notaryId      id do cartório
         */
        fun getManagerDefaultPermissions(userId: UUID, notaryId: UUID): HashSet<Permission> {
            return hashSetOf(
                Permission(Subject.UserAccount, userId),
                Permission(Subject.CivilRegistry, notaryId),
                Permission(Subject.Notary, notaryId)
            )
        }

        /**
         * Retorna a HashSet de permissões padrão do usuário gerente
         *
         * @param userId        id do usuário
         * @param notaryId      id do cartório
         */
        fun getSysadminDefaultPermissions(userId: UUID): HashSet<Permission> {
            return hashSetOf(
                Permission(Subject.UserAccount, userId),
                Permission(Subject.Notaries, null),
                Permission(Subject.Blockchain, null)
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        other as Permission
        return other.subject == subject && other.domainId == domainId
    }

    override fun hashCode(): Int {
        var result = subject.hashCode()
        result = 31 * result + (domainId?.hashCode() ?: 0)
        return result
    }
}