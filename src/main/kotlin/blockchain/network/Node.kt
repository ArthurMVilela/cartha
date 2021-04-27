package blockchain.network

import blockchain.Blockchain
import blockchain.Transaction
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import kotlin.random.Random

/**
 * Representa um nó na rede de blockchain
 */
class Node (
    var id:String?,
    val chain: Blockchain,
    val address: String,
    val notaryId: String
){
    constructor(address: String, notaryId: String):this(null, Blockchain(), address, notaryId) {
        id = createId()
    }

    constructor(chain: Blockchain, address: String, notaryId: String):this(null, chain, address, notaryId) {
        id = createId()
    }

    fun createId():String {
        val md = MessageDigest.getInstance("SHA")
        val now = LocalDateTime.now(ZoneOffset.UTC)
        var content = now.toString().toByteArray()
        content = content.plus(Random(now.toEpochSecond(ZoneOffset.UTC)).nextBytes(10))
        return Base64.getUrlEncoder().encodeToString(md.digest(content))
    }
}