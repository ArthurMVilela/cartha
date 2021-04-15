package document.persistency.dao

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SizedIterable

abstract class CompanionDAO<Type, DAOType: Entity<ID>, ID:Comparable<ID>, T: IdTable<ID>>(table: IdTable<ID>)
    : EntityClass<ID, DAOType>(table) {
    abstract fun insert(obj: Type): DAOType
    abstract fun select(id: ID): DAOType?
    abstract fun selectMany(id: ID): SizedIterable<DAOType>
    abstract fun selectAll(id: ID): SizedIterable<DAOType>
    abstract fun update(obj: Type)
    abstract fun remove(id: ID)
    abstract fun removeWhere(condition: Op<Boolean>)
}