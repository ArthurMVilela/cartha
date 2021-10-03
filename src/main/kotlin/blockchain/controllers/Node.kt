package blockchain.controllers

import blockchain.Block
import blockchain.BlockInfo
import blockchain.Blockchain
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import persistence.ResultSet
import util.serializer.UUIDSerializer
import java.util.*

/**
 * Representa um nó na rede de blockchain
 */
@Serializable
class Node (
    @Serializable(with = UUIDSerializer::class)
    val id: UUID = createId(),
    @Transient val chain: Blockchain = Blockchain(),
    @Serializable(with = UUIDSerializer::class)
    val notaryId: UUID
){
    constructor(notaryId: UUID):this(createId(), Blockchain(), notaryId)
    constructor(chain: Blockchain, notaryId: UUID):this(createId(), chain, notaryId)

    companion object {
        /**
         * Cria o identificador único para este nó
         *
         * @return UUID para a id do nó
         */
        private fun createId(): UUID {
            return UUID.randomUUID()
        }
    }

    fun getBlock(id: UUID): Block? {
        return chain.getBlock(id)
    }

    fun getLastBlock(): Block? {
        return chain.getLast()
    }

    fun getBlocks(page: Int): ResultSet<BlockInfo> {
        return chain.getBlocks(page)
    }

    fun validateChain(): Boolean {
        return chain.validateChain()
    }

}