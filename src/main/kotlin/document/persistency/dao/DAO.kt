package document.persistency.dao

import org.jetbrains.exposed.sql.Op

/**
 * Interface que encapsula os métodos para gerenciar a conexão com o banco de dados
 * para manipular dados da persistência de uma classe entidade.
 *
 * @param T     Classe entidade
 * @param ID    Tipo que representa a id da entidade
 */
interface DAO<T, ID> {
    fun insert(obj:T)
    fun select(id:ID):T?
    fun selectMany(condition: Op<Boolean>):List<T>
    fun selectAll():List<T>
    fun update(oldId:ID, new:T):T
    fun delete(id:ID)
    fun deleteWhere(condition: Op<Boolean>)
}