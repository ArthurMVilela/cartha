package authentication

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
    var permissions: MutableList<Permission>,
    var status: UserStatus
) {
    companion object {
        fun createClient(name: String, email: String, password: String):User {
            val user = User(name, email, Role.Client, mutableListOf(), password)
            val permissions = mutableListOf<Permission>()

            permissions.add(Permission(user.id!!, Subject.PersonalDocument, user.id.toString()))

            user.permissions = permissions
            return user
        }

        fun createOfficial(name: String, email: String, password: String, notaryId:String):User {
            val user = User(name, email, Role.Official, mutableListOf(), password)
            val permissions = mutableListOf<Permission>()

            permissions.add(Permission(user.id!!,Subject.CivilRegistry, notaryId))

            user.permissions = permissions
            return user
        }

        fun createManager(name: String, email: String, password: String, notaryId:String):User {
            val user = User(name, email, Role.Manager, mutableListOf(), password)
            val permissions = mutableListOf<Permission>()

            permissions.add(Permission(user.id!!,Subject.CivilRegistry, notaryId))
            permissions.add(Permission(user.id!!,Subject.Notary, notaryId))

            user.permissions = permissions
            return user
        }

        fun createSysAdmin(name: String, email: String, password: String,):User {
            val user = User(name, email,  Role.SysAdmin, mutableListOf(), password)
            val permissions = mutableListOf<Permission>()

            permissions.add(Permission(user.id!!, Subject.Notaries, null))
            permissions.add(Permission(user.id!!, Subject.Blockchain, null))

            user.permissions = permissions
            return user
        }
    }

    constructor(
        name: String,
        email: String,
        role: Role,
        permissions: MutableList<Permission>,
        password: String
    ):this(null, name, email, null, null, role, permissions, UserStatus.Offline){
        id = createId()

        salt = createSalt()
        pass = createPass(password)
    }

    fun validatePassword(password: String):Boolean {
        println("$pass ${createPass(password)}")
        return pass == createPass(password)
    }

    private fun createSalt():String {
        val now = LocalDateTime.now(ZoneOffset.UTC)
        val bytes = Random(now.toEpochSecond(ZoneOffset.UTC)).nextBytes(24)
        val md = MessageDigest.getInstance("SHA")

        return Base64.getUrlEncoder().encodeToString(md.digest(bytes))
    }

    private fun createPass(password: String):String {
        val md = MessageDigest.getInstance("SHA-256")
        val content = password + salt

        return Base64.getUrlEncoder().encodeToString(md.digest(content.toByteArray()))
    }

    private fun createId():UUID {
        return UUID.randomUUID()
    }
}