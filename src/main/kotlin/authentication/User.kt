package authentication

import authentication.exception.InvalidPasswordException
import authentication.exception.UserDeactivatedException
import authentication.exception.UserOfflineException
import authentication.exception.UserOnlineException
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import util.serializer.UUIDSerializer
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import kotlin.random.Random

/**
 * Representa um usuário do sistema
 *
 * @property id             identificador único do usuário
 * @property name           nome do usuário
 * @property email          email do usuário
 * @property salt           salt para a validação do usuário
 * @property pass           pass para a validação do usuário
 * @property role           função/cargo do usuário
 * @property permissions    permissões do usuário
 * @property status         status do conta de usuário
 */
@Serializable
class User(
    @Serializable(with = UUIDSerializer::class)
    var id: UUID?,
    val name: String,
    val email: String,
    @Transient internal var salt: String? = null,
    @Transient internal var pass: String? = null,
    val role: Role,
    var permissions: HashSet<Permission>,
    var status: UserStatus
) {
    constructor(
        name: String,
        email: String,
        role: Role,
        permissions: HashSet<Permission>,
        password: String
    ):this(null, name, email, null, null, role, permissions, UserStatus.Offline){
        id = createId()

        salt = createSalt()
        pass = createPass(password)
    }

    companion object {
        fun createClient(name: String, email: String, password: String):User {
            val user = User(name, email, Role.Client, hashSetOf(), password)
            user.permissions = Permission.getClientDefaultPermissions(user.id!!)
            return user
        }

        fun createOfficial(name: String, email: String, password: String, notaryId:UUID):User {
            val user = User(name, email, Role.Official, hashSetOf(), password)
            user.permissions = Permission.getOfficialDefaultPermissions(user.id!!, notaryId)
            return user
        }

        fun createManager(name: String, email: String, password: String, notaryId:UUID):User {
            val user = User(name, email, Role.Manager, hashSetOf(), password)
            user.permissions = Permission.getManagerDefaultPermissions(user.id!!, notaryId)
            return user
        }

        fun createSysAdmin(name: String, email: String, password: String,):User {
            val user = User(name, email,  Role.SysAdmin, hashSetOf(), password)
            user.permissions = Permission.getSysadminDefaultPermissions(user.id!!)
            return user
        }
    }

    /**
     * verifica se a senha dada é correta
     *
     * @param password          senha a ser validada
     * @return se a senha é valida
     */
    fun validatePassword(password: String):Boolean {
        return pass == createPass(password)
    }

    /**
     * Adiciona uma permissão às permissões do usuário
     *
     * @param permission        permissão a ser adicionada para o usuário
     */
    fun addPermission(permission: Permission) {
        permissions.add(permission)
    }

    /**
     * Adiciona uma colleção de permissões às permissões do usuário
     *
     * @param permissions       permissões a serem adicionadas ao usuário
     */
    fun addPermissions(permissions: Collection<Permission>) {
        permissions.forEach {
            addPermission(it)
        }
    }

    /**
     * Remove todas as permissões do usuário
     */
    fun removeAllPermissions() {
        permissions.removeAll { _ -> true }
    }

    /**
     * Remove uma permissão do usuário
     *
     * @param permission        permissão a ser retirada do usuário
     */
    fun removePermission(permission: Permission) {
        permissions.remove(permission)
    }

    /**
     * Busca uma permissão nas permissões do usuário
     *
     * @param subject           Assunto da permissão
     * @param domainId          Id do dominio da permissão
     *
     * @return a permissão encontrada ou nulo, caso não tenha sido encontrado
     */
    fun getPermission(subject: Subject, domainId: UUID?): Permission? {
        return permissions.firstOrNull { p -> p.subject == subject && p.domainId == domainId }
    }

    /**
     * Muda senha da conta de usuário
     *
     * @param oldPassword       senha atual (antiga)
     * @param newPassword       nova senha
     */
    fun changePassword(oldPassword: String, newPassword: String) {
        if (!validatePassword(oldPassword)) {
            throw InvalidPasswordException()
        }

        salt = createSalt()
        pass = createPass(newPassword)
    }

    /**
     * Deixa a conta de usuário online
     */
    fun login() {
        when (status) {
            UserStatus.Online -> throw UserOnlineException("Usuário já online.")
            UserStatus.Deactivated -> throw UserDeactivatedException("Conta de usuário desativada.")
            else -> status = UserStatus.Online
        }
    }

    /**
     * Deixa a conta de usuário offline
     */
    fun logout() {
        when (status) {
            UserStatus.Offline -> throw UserOfflineException("Usuário já offline.")
            UserStatus.Deactivated -> throw UserDeactivatedException("Conta de usuário desativada.")
            else -> status = UserStatus.Offline
        }
    }

    /**
     * Desativa a conta de usuário
     */
    fun deactivateAccount() {
        when (status) {
            UserStatus.Online -> throw UserOnlineException("Usuário online, termine sessão para que a conta possa ser desativada")
            UserStatus.Deactivated -> throw UserDeactivatedException("Conta de usuário já desativada.")
            else -> status = UserStatus.Deactivated
        }
    }

    /**
     * Reativa a conta de usuário
     */
    fun reactivateAccount() {
        when (status) {
            UserStatus.Online -> throw UserOnlineException("Conta já está ativada.")
            UserStatus.Offline -> throw UserOfflineException("Conta já está ativada.")
            else -> status = UserStatus.Offline
        }
    }

    /**
     * Cria um valor salt para esta conta
     *
     * @return valor salt (hash)
     */
    private fun createSalt():String {
        val now = LocalDateTime.now(ZoneOffset.UTC)
        val bytes = Random(now.toEpochSecond(ZoneOffset.UTC)).nextBytes(24)
        val md = MessageDigest.getInstance("SHA")

        return Base64.getUrlEncoder().encodeToString(md.digest(bytes))
    }

    /**
     * Cria um valor pass (hash(senha + salt)
     *
     * @param password          senha
     * @return valor pass
     */
    private fun createPass(password: String):String {
        val md = MessageDigest.getInstance("SHA-256")
        val content = password + salt

        return Base64.getUrlEncoder().encodeToString(md.digest(content.toByteArray()))
    }

    /**
     * Cria o identificador único para esta conta
     *
     * @return UUID para a id do usuário
     */
    private fun createId():UUID {
        return UUID.randomUUID()
    }
}