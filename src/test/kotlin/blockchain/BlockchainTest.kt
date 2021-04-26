package blockchain

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class BlockchainTest {
    @Test
    internal fun testValidate() {
        val blockchain = Blockchain()
        val last = blockchain.getLast()
        println(Json.encodeToString(last))
        val newBlock1 = Block(LocalDateTime.now(), listOf(), last.hash!!)
        println(Json.encodeToString(newBlock1))
        val newBlock2 = Block(LocalDateTime.now(), listOf(), newBlock1.hash!!)
        println(Json.encodeToString(newBlock2))

        blockchain.addBlock(newBlock1)
        blockchain.addBlock(newBlock2)

        assert(blockchain.validateChain())
    }
}