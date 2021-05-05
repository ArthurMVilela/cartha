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
 * @property id         identificador único do usuário
 * @property name       nome do usuário
 * @property email      email do usuário
 * @property cpf        cpf dp usuário
 * @property salt       salt para a validação do usuário
 * @property pass       pass para a validação do usuário
 */
@Serializable
class User(
    var id: String?,
    var name: String,
    var email: String?,
    var cpf: String?,
    var salt: String?,
    var pass: String?,
) {
    constructor(
        name: String,
        email: String?,
        cpf: String?,
        password: String
    ):this(null, name, email, cpf, null, null,){
        id = createId()

        salt = createSalt()
        pass = createPass(password)
    }

    fun validatePassword(password: String):Boolean {
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