package blockchain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class BlockTest {
    @Test
    internal fun testCreateBlock() {
        val block = Block(
            LocalDateTime.now(),
            listOf(
                Transaction(LocalDateTime.now(), "", "", TransactionType.Creation)
            ),
            ""
        )

    }
}