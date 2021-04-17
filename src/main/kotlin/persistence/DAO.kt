package persistence

interface DAO<Type> {
    fun toType():Type?
}