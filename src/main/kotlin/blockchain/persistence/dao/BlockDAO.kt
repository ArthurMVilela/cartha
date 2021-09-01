package blockchain.persistence.dao

import blockchain.Block
import newPersistence.DAO
import newPersistence.ResultSet
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import java.util.*

class BlockDAO:DAO<Block, UUID> {
    override fun insert(obj: Block): Block {
        TODO("Not yet implemented")
    }

    override fun select(id: UUID): Block? {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>, page: Int, pageLength: Int): ResultSet<Block> {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>): List<Block> {
        TODO("Not yet implemented")
    }

    override fun selectAll(page: Int, pageLength: Int): ResultSet<Block> {
        TODO("Not yet implemented")
    }

    override fun update(obj: Block) {
        TODO("Not yet implemented")
    }

    override fun remove(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun removeWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }

    override fun toType(row: ResultRow): Block {
        TODO("Not yet implemented")
    }
}