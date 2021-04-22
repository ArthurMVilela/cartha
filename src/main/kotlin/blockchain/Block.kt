package blockchain

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import util.serializer.LocalDateTimeSerializer
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import kotlin.random.Random

@Serializable
class Block(
    var id:String?,
    @Serializable(with = LocalDateTimeSerializer::class)
    val timestamp: LocalDateTime,
    var transactionsHash: String?,
    val transactions: List<Transaction>,
    val previousHash: String,
    var hash: String?
) {
    fun createId(): String {
        val md = MessageDigest.getInstance("SHA")
        val now = LocalDateTime.now(ZoneOffset.UTC)
        var content = now.toString().toByteArray()
        content = content.plus(Random(now.toEpochSecond(ZoneOffset.UTC)).nextBytes(10))
        return Base64.getUrlEncoder().encodeToString(md.digest(content))
    }

    fun createHash():String {
        val md = MessageDigest.getInstance("SHA-256")
        val content = Json.encodeToString(this).toByteArray()
        return Base64.getUrlEncoder().encodeToString(md.digest(content))
    }
}