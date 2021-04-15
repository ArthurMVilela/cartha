package document.persistency.dao

interface DAO<Type> {
    fun toType():Type?
}