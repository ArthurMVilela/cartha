package blockchain

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import util.serializer.LocalDateTimeSerializer
import util.serializer.UUIDSerializer
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import kotlin.random.Random

@Serializable
class Block(
    @Serializable(with = UUIDSerializer::class)
    var id:UUID,
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
        createId(),
        timestamp,
        transactions,
        null,
        previousHash,
        null,
        nodeId
    ) {
        this.transactionsHash = createTransactionsHash()
        this.hash = createHash()
    }

    companion object {
        /**
         * Cria o identificador único para o bloco
         *
         * @return UUID para a id do usuário
         */
        private fun createId():UUID {
            return UUID.randomUUID()
        }
    }

    fun createHash():String {
        val md = MessageDigest.getInstance("SHA-256")
        var content = Base64.getUrlDecoder().decode(id.toString().toByteArray())
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