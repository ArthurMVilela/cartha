package authentication

import kotlinx.serialization.Serializable
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
 * @property cpf            cpf dp usuário
 * @property salt           salt para a validação do usuário
 * @property pass           pass para a validação do usuário
 * @property role           função/cargo do usuário
 * @property permissions    permissções do usuário
 */
@Serializable
class User(
    var id: String?,
    var name: String,
    var email: String?,
    var cpf: String?,
    var salt: String?,
    var pass: String?,
    val role: Role,
    var permissions: List<Permission>
) {
    companion object {
        fun createClient(name: String, email: String, cpf: String, password: String):User {
            val user = User(name, email, cpf, Role.Client, listOf(), password)
            val permissions = mutableListOf<Permission>()

            permissions.add(Permission(user.id!!, Subject.PersonalDocument, user.id))

            user.permissions = permissions
            return user
        }

        fun createOfficial(name: String, email: String, cpf: String, password: String, notaryId:String):User {
            val user = User(name, email, cpf, Role.Official, listOf(), password)
            val permissions = mutableListOf<Permission>()

            permissions.add(Permission(user.id!!,Subject.CivilRegistry, notaryId))

            user.permissions = permissions
            return user
        }

        fun createManager(name: String, email: String, cpf: String, password: String, notaryId:String):User {
            val user = User(name, email, cpf, Role.Manager, listOf(), password)
            val permissions = mutableListOf<Permission>()

            permissions.add(Permission(user.id!!,Subject.CivilRegistry, notaryId))
            permissions.add(Permission(user.id!!,Subject.Notary, notaryId))

            user.permissions = permissions
            return user
        }

        fun createSysAdmin(name: String, email: String, cpf: String, password: String,):User {
            val user = User(name, email, cpf, Role.SysAdmin, listOf(), password)
            val permissions = mutableListOf<Permission>()

            permissions.add(Permission(user.id!!, Subject.Notaries, null))

            user.permissions = permissions
            return user
        }
    }

    constructor(
        name: String,
        email: String,
        cpf: String,
        role: Role,
        permissions: List<Permission>,
        password: String
    ):this(null, name, email, cpf, null, null, role, permissions){
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

    private fun createId():String {
        val md = MessageDigest.getInstance("SHA-256")
        val now = LocalDateTime.now(ZoneOffset.UTC)
        var content = now.toString().toByteArray()
        content = content.plus(Random(now.toEpochSecond(ZoneOffset.UTC)).nextBytes(10))
        return Base64.getUrlEncoder().encodeToString(md.digest(content))
    }
}