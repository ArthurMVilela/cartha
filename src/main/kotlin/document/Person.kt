package document

import kotlinx.serialization.Serializable
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

/**
 * Representa uma pessoa física que se relaciona a documentos
 *
 * @property id     identificador único
 * @property name   nome do indivíduo
 */
@Serializable
abstract class Person (
) {
    abstract var id: String?
    abstract val name: String

    fun createId(): String {
        val md = MessageDigest.getInstance("SHA")
        val now = LocalDateTime.now(ZoneOffset.UTC)
        return Base64.getUrlEncoder().encodeToString(md.digest(now.toString().toByteArray()))
    }
}