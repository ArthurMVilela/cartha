package blockchain.controllers

import blockchain.Blockchain
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
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
    val notaryId: String
){
    constructor(notaryId: String):this(createId(), Blockchain(), notaryId)
    constructor(chain: Blockchain, notaryId: String):this(createId(), chain, notaryId)

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

}