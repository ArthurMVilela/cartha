package document.persistence.dao.civilRegistry

import document.persistence.tables.DocumentTable
import document.persistence.tables.civilRegistry.CivilRegistryDocumentTable
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import persistence.DAO
import persistence.ResultSet
import java.util.*

class CivilRegistryDAO: DAO<CivilRegistryDAO, UUID> {
    override fun insert(obj: CivilRegistryDAO): CivilRegistryDAO {
        TODO("Not yet implemented")
    }

    override fun select(id: UUID): CivilRegistryDAO? {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>, page: Int, pageLength: Int): ResultSet<CivilRegistryDAO> {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>): List<CivilRegistryDAO> {
        TODO("Not yet implemented")
    }

    override fun selectAll(page: Int, pageLength: Int): ResultSet<CivilRegistryDAO> {
        TODO("Not yet implemented")
    }

    override fun update(obj: CivilRegistryDAO) {
        TODO("Not yet implemented")
    }

    override fun remove(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun removeWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }

    override fun toType(row: ResultRow): CivilRegistryDAO {
        TODO("Not yet implemented")
    }

    fun civilRegistryCount(notaryId: UUID): Int {
        val table = DocumentTable.join(
            CivilRegistryDocumentTable,
            JoinType.INNER,
            additionalConstraint = {DocumentTable.id eq CivilRegistryDocumentTable.id}
        )

        return transaction { table.select { DocumentTable.notaryId eq notaryId }.count().toInt() }
    }
}