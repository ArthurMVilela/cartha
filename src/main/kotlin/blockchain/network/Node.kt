package blockchain.network

import blockchain.Blockchain
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
    val notaryId: String
){
    constructor(notaryId: String):this(null, Blockchain(), notaryId) {
        id = createId()
    }

    constructor(chain: Blockchain, notaryId: String):this(null, chain, notaryId) {
        id = createId()
    }

    init {
        chain.addBlock(LocalDateTime.now(), listOf())
        chain.addBlock(LocalDateTime.now(), listOf())
        chain.addBlock(LocalDateTime.now(), listOf())
        chain.addBlock(LocalDateTime.now(), listOf())
    }

    private fun createId():String {
        val md = MessageDigest.getInstance("SHA")
        val now = LocalDateTime.now(ZoneOffset.UTC)
        var content = now.toString().toByteArray()
        content = content.plus(Random(now.toEpochSecond(ZoneOffset.UTC)).nextBytes(10))
        return Base64.getUrlEncoder().encodeToString(md.digest(content))
    }
}