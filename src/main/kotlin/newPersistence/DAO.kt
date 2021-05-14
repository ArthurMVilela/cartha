package newPersistence

import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow

interface DAO<Type, ID:Comparable<ID>>{
    fun insert(obj: Type): Type
    fun select(id: ID): Type?
    fun selectMany(condition: Op<Boolean>, page:Int=1, pageLength:Int=20): ResultSet<Type>
    fun selectAll(page:Int=1, pageLength:Int=20): ResultSet<Type>
    fun update(obj: Type)
    fun remove(id: ID)
    fun removeWhere(condition: Op<Boolean>)

    fun toType(row: ResultRow):Type
}