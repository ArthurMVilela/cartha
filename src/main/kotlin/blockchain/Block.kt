package blockchain

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
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
    val transactions: List<Transaction>,
    var transactionsHash: String?,
    val previousHash: String,
    var hash: String?,
    var nodeId: String?
) {
    constructor(
        timestamp: LocalDateTime,
        transactions: List<Transaction>,
        previousHash: String,
        nodeId: String?
    ):this(
        null,
        timestamp,
        transactions,
        null,
        previousHash,
        null,
        nodeId
    ) {
        this.id = createId()
        this.transactionsHash = createTransactionsHash()
        this.hash = createHash()
    }

    fun createId(): String {
        val md = MessageDigest.getInstance("SHA")
        val now = LocalDateTime.now(ZoneOffset.UTC)
        var content = now.toString().toByteArray()
        content = content.plus(Random(now.toEpochSecond(ZoneOffset.UTC)).nextBytes(10))
        return Base64.getUrlEncoder().encodeToString(md.digest(content))
    }

    fun createHash():String {
        val md = MessageDigest.getInstance("SHA-256")
        var content = Base64.getUrlDecoder().decode(id)
        content = content.plus(timestamp.toString().toByteArray())
        content = content.plus(Json.encodeToString(transactions).toByteArray())
        content = content.plus(transactionsHash!!.toByteArray())
        content = content.plus(previousHash.toByteArray())

        return Base64.getUrlEncoder().encodeToString(md.digest(content))
    }

    fun createTransactionsHash():String {
        val merkleTree = MerkleTree.createMerkleTree(transactions.map {
            Base64.getUrlDecoder().decode(it.hash)
        })

        return merkleTree.hash!!
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Block

        if (other.id != this.id) return false
        if (other.timestamp != this.timestamp) return false
        if (other.transactionsHash != this.transactionsHash) return false
        if (other.previousHash != this.previousHash) return false
        if (other.hash != this.hash) return false

        return true
    }
}