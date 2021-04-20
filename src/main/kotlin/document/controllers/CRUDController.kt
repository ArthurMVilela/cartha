package document.controllers

interface CRUDController<Type, ID> {
    fun create(new: Type): Type
    fun get(id:ID):Type?
    fun update(id: ID, new: Type):Type
    fun delete(id: ID)
}