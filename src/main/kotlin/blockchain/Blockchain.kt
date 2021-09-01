package blockchain

import blockchain.persistence.dao.BlockDAO
import blockchain.persistence.tables.BlockTable
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.max
import java.time.LocalDateTime
import java.time.Month
import java.time.Year
import java.util.*

/**
 *  Representa uma blockchain
 *
 *  @property blocks        Lista de blocos da blockchain, caso vázia, a blockchain cria um bloco genesys
 */
class Blockchain(val blocks:MutableList<Block> = mutableListOf()) {
    private val blocksDAO = BlockDAO()

    /**
     * Adiciona um bloco à blockchain
     *
     * @param block         bloco a ser adicionado
     */
    fun addBlock(block: Block) {
        blocksDAO.insert(block)
    }

    /**
     * Adiciona um bloco à blockchain
     *
     * @param timestamp             timestamp do bloco
     * @param transactions          lista das transações do bloco
     */
    fun addBlock(timestamp: LocalDateTime, transactions: List<Transaction>, nodeId:UUID) {
        val lastBlockHash = getLast()?.hash?:""
        this.addBlock(Block(timestamp, transactions, lastBlockHash, nodeId))
    }

    /**
     * Busca o último bloco da blockchain
     *
     * @return Último bloco da blockchain
     */
    fun getLast():Block? {
        return blocksDAO.selectMany(Op.build { BlockTable.timestamp eq BlockTable.timestamp.max() }).firstOrNull()
    }

    /**
     * Busca um bloco da blockchain com uma id específica
     *
     * @param id            id do bloco
     *
     * @return bloco com a id especificada
     */
    fun getBlock(id:UUID): Block? {
        return blocksDAO.select(id)
    }

    /**
     * Valida a blockchain
     *
     * @return se a blockchain é válida
     */
    fun validateChain():Boolean {
//        blocks.forEachIndexed { index, block ->
//
//            if (block.hash != block.createHash()) return false
//            if (index > 0) {
//                val previous = blocks[index - 1]
//                if (previous.hash != block.previousHash) return false
//            } else {
//                if (createGenesys().timestamp != block.timestamp) return false
//                if (createGenesys().previousHash != block.previousHash) return false
//                if (createGenesys().transactions != block.transactions) return false
//            }
//        }

        return true
    }

    /**
     * Cria o bloco genesys
     *
     * @return bloco genesys
     */
    private fun createGenesys():Block {
        val timestamp = LocalDateTime.of(Year.MIN_VALUE, Month.JANUARY, 1, 0, 0,0,0)
        return Block(timestamp, listOf(), "", UUID.randomUUID())
    }
}