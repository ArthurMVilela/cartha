package blockchain

import java.security.MessageDigest
import java.util.*

/**
 * Representa uma merkle tree, ou seja, uma arvore binária
 * em que cada nó é uma hash e o pai de cada no é a contanação
 * das hashs dos nós filhos.
 *
 */
class MerkleTree(
    var hash:String?,
    var parent:MerkleTree? = null,
    val left:MerkleTree? = null,
    val right:MerkleTree? = null
) {
    companion object {
        fun createMerkleTree(data:List<ByteArray>):MerkleTree {
            if (data.isEmpty()) {
                return MerkleTree(createHash("".toByteArray()))
            }
            val leaves = data.map {
                MerkleTree(createHash(it))
            }

            var root = false
            var gen = 1
            var currentGen = leaves

            while (!root) {
                if (currentGen.size == 1) {
                    root = true
                    break
                }

                val parents = mutableListOf<MerkleTree>()
                currentGen.forEachIndexed { index, merkleTree ->
                    if (index % 2 == 0) {
                        val nextIndex = index + 1

                        if (nextIndex >= currentGen.size) {
                            val new = MerkleTree(merkleTree.hash, left = merkleTree)
                            merkleTree.parent = new
                            parents.add(new)
                        } else {
                            val next = currentGen.get(nextIndex)

                            val new = MerkleTree(null, left = merkleTree, right = next)
                            val hash = (new.left!!.hash!! + new.right!!.hash!!).toByteArray()
                            new.hash = createHash(hash)
                            merkleTree.parent = new
                            next.parent = new
                            parents.add(new)
                        }

                    }

                }
                currentGen = parents

                gen++
            }

            return currentGen.first()
        }

        private fun createHash(value:ByteArray):String {
            val md = MessageDigest.getInstance("SHA-256")
            return Base64.getUrlEncoder().encodeToString(md.digest(value))
        }
    }

    fun isRoot():Boolean {
        return parent === null
    }
}