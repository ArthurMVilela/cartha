package blockchain

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.Month
import java.time.Year
import java.util.*

/**
 *  Representa uma blockchain
 *
 *  @property blocks        Lista de blocos da blockchain, caso vázia, a blockchain cria um bloco genesys
 */
@Serializable
class Blockchain(private val blocks:MutableList<Block> = mutableListOf()) {
    init {
        if (blocks.isEmpty()) {
            val genesys = createGenesys()
            blocks.add(genesys)
        }
    }

    /**
     * Adiciona um bloco à blockchain
     *
     * @param block         bloco a ser adicionado
     */
    fun addBlock(block: Block) {
        blocks.add(block)
    }

    /**
     * Adiciona um bloco à blockchain
     *
     * @param timestamp             timestamp do bloco
     * @param transactions          lista das transações do bloco
     */
    fun addBlock(timestamp: LocalDateTime, transactions: List<Transaction>) {
        this.addBlock(Block(timestamp, transactions, getLast().hash!!))
    }

    /**
     * Busca o último bloco da blockchain
     *
     * @return Último bloco da blockchain
     */
    fun getLast():Block {
        return blocks.last()
    }

    /**
     * Busca um bloco da blockchain com uma id específica
     *
     * @param id            id do bloco
     *
     * @return bloco com a id especificada
     */
    fun getBlock(id:String): Block {
        return blocks.first { it.id == id }
    }

    /**
     * Valida a blockchain
     *
     * @return se a blockchain é válida
     */
    fun validateChain():Boolean {
        blocks.forEachIndexed { index, block ->

            if (block.hash != block.createHash()) return false
            if (index > 0) {
                val previous = blocks[index - 1]
                if (previous.hash != block.previousHash) return false
            } else {
                if (createGenesys().timestamp != block.timestamp) return false
                if (createGenesys().previousHash != block.previousHash) return false
                if (createGenesys().transactions != block.transactions) return false
            }
        }

        return true
    }

    /**
     * Cria o bloco genesys
     *
     * @return bloco genesys
     */
    private fun createGenesys():Block {
        val timestamp = LocalDateTime.of(Year.MIN_VALUE, Month.JANUARY, 1, 0, 0,0,0)
        return Block(timestamp, listOf(), "")
    }
}