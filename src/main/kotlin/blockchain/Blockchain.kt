package blockchain

import blockchain.persistence.dao.BlockDAO
import blockchain.persistence.dao.BlockInfoDAO
import blockchain.persistence.dao.TransactionDAO
import blockchain.persistence.tables.BlockTable
import org.jetbrains.exposed.sql.Op
import persistence.ResultSet
import java.time.LocalDateTime
import java.util.*

/**
 *  Representa uma blockchain
 *
 *  @property blocks        Lista de blocos da blockchain, caso vázia, a blockchain cria um bloco genesys
 */
class Blockchain(val blocks:MutableList<Block> = mutableListOf()) {
    private val blocksDAO = BlockDAO()
    private val blocksInfoDAO = BlockInfoDAO()
    private val transactions = TransactionDAO()

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
        transactions.forEach {
            it.pending = false
        }
        this.addBlock(Block(timestamp, transactions, lastBlockHash, nodeId))
    }

    /**
     * Busca o último bloco da blockchain
     *
     * @return Último bloco da blockchain
     */
    fun getLast():Block? {
        return blocksDAO.selectLastBlock()
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

    fun getBlocks(page: Int): ResultSet<BlockInfo> {
        return blocksInfoDAO.selectAll(page)
    }

    /**
     * Valida a blockchain
     *
     * @return se a blockchain é válida
     */
    fun validateChain():Boolean {
        var block = getLast()

        while (block != null) {
            val previousHash = block.previousHash

            val previous = if(previousHash != "") {
                blocksDAO.selectMany(Op.build { BlockTable.hash eq previousHash }).firstOrNull()?:return false
            } else {
                null
            }

            println("${block.hash} | ${block.createHash()}")
            if (block.hash != block.createHash()) return false

            if (previous != null) {
                println("${block.previousHash} | ${previous.createHash()}")
                if (block.previousHash != previous.createHash()) return false
            }


            block = previous
        }

        return true
    }

    /**
     * Cria o bloco genesys
     *
     * @return bloco genesys
     */
    fun createGenesys(nodeId: UUID):Block {
        val timestamp = LocalDateTime.now()
        return Block(timestamp, listOf(), "", nodeId)
    }
}