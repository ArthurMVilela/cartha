package newPersistence

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SizedIterable

sealed class DAOCompanion<Type, DAOType: Entity<ID>, ID:Comparable<ID>, T: IdTable<ID>>(table: IdTable<ID>)
    :EntityClass<ID, DAOType>(table) {

    abstract fun insert(obj: Type): DAOType
    abstract fun select(id: ID): DAOType?
    abstract fun selectMany(condition: Op<Boolean>, page:Int=1, pageLength:Int=20): SizedIterable<DAOType>
    abstract fun selectAll(): SizedIterable<DAOType>
    abstract fun update(obj: Type)
    abstract fun remove(id: ID)
    abstract fun removeWhere(condition: Op<Boolean>)

    abstract fun toResultSet(result: SizedIterable<DAOType>, page:Int, pageLength: Int, numberOfPages:Int):ResultSet<Type>
}