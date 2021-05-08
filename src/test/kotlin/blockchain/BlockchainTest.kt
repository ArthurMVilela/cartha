package blockchain

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class BlockchainTest {
    @Test
    @DisplayName("Testar validadade da blockchain")
    internal fun testValidate() {
        val blockchain = Blockchain()
        val last = blockchain.getLast()
        val newBlock1 = Block(LocalDateTime.now(), listOf(), last.hash!!,"")
        val newBlock2 = Block(LocalDateTime.now(), listOf(), newBlock1.hash!!,"")

        blockchain.addBlock(newBlock1)
        blockchain.addBlock(newBlock2)

        assert(blockchain.validateChain())
    }

    @Test
    @DisplayName("Teste buscar bloco")
    internal fun testGetBlock() {
        val blockchain = Blockchain()
        val last = blockchain.getLast()
        val newBlock1 = Block(LocalDateTime.now(), listOf(), last.hash!!, "")
        val newBlock2 = Block(LocalDateTime.now(), listOf(), newBlock1.hash!!, "")

        blockchain.addBlock(newBlock1)
        blockchain.addBlock(newBlock2)

        assert(blockchain.getBlock(newBlock1.id!!) == newBlock1)
        assert(blockchain.getBlock(newBlock2.id!!) == newBlock2)
        assert(blockchain.getBlock(last.id!!) == last)
    }
}