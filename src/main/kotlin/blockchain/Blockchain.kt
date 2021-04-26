package blockchain

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.Month
import java.time.Year
import java.util.*

@Serializable
class Blockchain(val blocks:MutableList<Block> = mutableListOf()) {
    init {
        if (blocks.isEmpty()) {
            val genesys = createGenesys()
            blocks.add(genesys)
        }
    }

    fun addBlock(block: Block) {
        blocks.add(block)
    }

    fun getLast():Block {
        return blocks.last()
    }

    fun validateChain():Boolean {
        blocks.forEachIndexed { index, block ->
            if (block.hash != block.createHash()) return false
            if (index > 0) {
                val previous = blocks[index - 1]
                if (previous.hash != block.previousHash) return false
            } else {
                if (createGenesys() != block) return false
            }
        }

        return true
    }

    private fun createGenesys():Block {
        val id = Base64.getUrlEncoder().encodeToString(byteArrayOf())
        val timestamp = LocalDateTime.of(Year.MIN_VALUE, Month.JANUARY, 1, 0, 0,0,0)
        return Block(id, timestamp, listOf(), "", "", "")
    }
}