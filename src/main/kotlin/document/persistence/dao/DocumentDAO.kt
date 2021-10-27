package document.persistence.dao

import document.Document
import document.DocumentStatus
import document.persistence.tables.DocumentTable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import persistence.DAO
import persistence.ResultSet
import util.serializer.UUIDSerializer
import java.util.*

class DocumentDAO:DAO<Document, UUID> {
    override fun insert(obj: Document): Document {
        TODO("Not yet implemented")
    }

    override fun select(id: UUID): Document? {
        var found: Document? = null

        transaction {
            try {
                val row = DocumentTable.select(Op.build { DocumentTable.id eq id }).firstOrNull()?:return@transaction
                found = toType(row)
            }catch (ex: Exception) {
                rollback()
                throw ex
            }
        }

        return found
    }

    override fun selectMany(condition: Op<Boolean>, page: Int, pageLength: Int): ResultSet<Document> {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>): List<Document> {
        TODO("Not yet implemented")
    }

    override fun selectAll(page: Int, pageLength: Int): ResultSet<Document> {
        TODO("Not yet implemented")
    }

    override fun update(obj: Document) {
        TODO("Not yet implemented")
    }

    override fun remove(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun removeWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }

    override fun toType(row: ResultRow): Document {
        val id = row[DocumentTable.id].value
        val status = row[DocumentTable.status]
        val officialId = row[DocumentTable.officialId].value
        val notaryId = row[DocumentTable.notaryId].value
        val hash = row[DocumentTable.hash]


        return DocumentImpl(id, status, officialId, notaryId, hash)
    }

    @Serializable
    internal class DocumentImpl(
        @Serializable(with = UUIDSerializer::class)
        override val id: UUID,
        override var status: DocumentStatus,
        @Serializable(with = UUIDSerializer::class)
        @SerialName("official_id")
        override val officialId: UUID,
        @Serializable(with = UUIDSerializer::class)
        @SerialName("notary_id")
        override val notaryId: UUID,
        override var hash: String?
        ): Document() {

        override fun createHash(): String {
            TODO("Not yet implemented")
        }
    }
}