package blockchain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.security.MessageDigest
import java.time.LocalDateTime
import java.util.*

internal class BlockTest {
    private val sha256Length = MessageDigest.getInstance("SHA-256").digestLength
    private val sha256EncodedLength = Base64.getUrlEncoder().encodeToString(MessageDigest.getInstance("SHA-256").digest()).length
    private val shaLength = MessageDigest.getInstance("SHA").digestLength
    private val shaEncodedLength = Base64.getUrlEncoder().encodeToString(MessageDigest.getInstance("SHA").digest()).length

    @Test
    @DisplayName("Criar bloco")
    internal fun testCreateBlock() {
        val block = Block(
            LocalDateTime.now(),
            listOf(
                Transaction(LocalDateTime.now(), "", "", TransactionType.Creation)
            ),
            ""
        )

        assert(!block.id.isNullOrEmpty())
        assert(block.id?.length == shaEncodedLength)
        assert(Base64.getUrlDecoder().decode(block.id).size == shaLength)

        assert(!block.transactionsHash.isNullOrEmpty())
        assert(block.transactionsHash?.length == sha256EncodedLength)
        assert(Base64.getUrlDecoder().decode(block.transactionsHash).size == sha256Length)

        assert(!block.hash.isNullOrEmpty())
        assert(block.hash?.length == sha256EncodedLength)
        assert(Base64.getUrlDecoder().decode(block.hash).size == sha256Length)
    }

    @Test
    @DisplayName("Criar blocos e verificar hashs")
    internal fun testBlockHash() {
        val now = LocalDateTime.now()
        val transactions = listOf<Transaction>(
            Transaction(now, "", "", TransactionType.Creation)
        )
        val blockA = Block(now, transactions, "")
        val blockB = Block(LocalDateTime.now(), transactions, blockA.hash!!)

        assertNotEquals(blockA.id, blockB.id)
        assertNotEquals(blockA.timestamp, blockB.timestamp)
        assertEquals(blockA.hash, blockB.previousHash)
        assertNotEquals(blockA.previousHash, blockB.previousHash)

    }
}