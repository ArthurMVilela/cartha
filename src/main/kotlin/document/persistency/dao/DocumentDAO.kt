package document.persistency.dao

import document.Document
import org.jetbrains.exposed.sql.Op

class DocumentDAO:DAO<Document, String> {
    override fun insert(obj: Document) {
        TODO("Not yet implemented")
    }

    override fun select(id: String): Document? {
        TODO("Not yet implemented")
    }

    override fun selectMany(condition: Op<Boolean>): List<Document> {
        TODO("Not yet implemented")
    }

    override fun selectAll(): List<Document> {
        TODO("Not yet implemented")
    }

    override fun update(oldId: String, new: Document) {
        TODO("Not yet implemented")
    }

    override fun delete(id: String) {
        TODO("Not yet implemented")
    }

    override fun deleteWhere(condition: Op<Boolean>) {
        TODO("Not yet implemented")
    }
}