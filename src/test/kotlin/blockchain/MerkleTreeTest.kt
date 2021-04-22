package blockchain

import org.junit.jupiter.api.Test

internal class MerkleTreeTest {
    @Test
    internal fun testCreateMerkleTree() {
        val root = MerkleTree.createMerkleTree(listOf(
            "test1".toByteArray(),
            "test2".toByteArray(),
            "test3".toByteArray(),
            "test4".toByteArray(),
            "test5".toByteArray(),
        ))

        assert(root.isRoot())
        checkChildrenAreNotRoot(listOf(root.left!!, root.right!!))
    }

    private fun checkChildrenAreNotRoot(merkleTrees:List<MerkleTree>) {
        val gen = mutableListOf<MerkleTree>()
        merkleTrees.forEach {
            assert(!it.isRoot())
            if (it.left != null) {
                gen.add(it.left!!)
            }
            if (it.right != null) {
                gen.add(it.right!!)
            }
        }
        if (gen.size > 0) {
            checkChildrenAreNotRoot(gen)
        }
    }
}