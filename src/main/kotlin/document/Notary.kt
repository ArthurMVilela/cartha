package document

import kotlinx.serialization.Serializable
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import kotlin.random.Random

/**
 * Representa um cartório
 *
 * @property id     identificador único
 * @property name   nome (razão social) do cartório
 * @property cnpj   CNPJ do cartório
 * @property cns    Cadastro Nacional de Servidão do cartório
 */
@Serializable
class Notary (
    var id: String?,
    val name: String,
    val cnpj: String,
    val cns: String
) {
    fun createId():String {
        val md = MessageDigest.getInstance("SHA")
        val now = LocalDateTime.now(ZoneOffset.UTC)
        var content = now.toString().toByteArray()
        content = content.plus(Random(now.toEpochSecond(ZoneOffset.UTC)).nextBytes(10))
        return Base64.getUrlEncoder().encodeToString(md.digest(content))
    }
}