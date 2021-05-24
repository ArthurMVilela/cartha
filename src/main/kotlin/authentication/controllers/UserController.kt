package authentication.controllers

import authentication.Permission
import authentication.Role
import authentication.User
import authentication.UserSession
import authentication.persistence.dao.PermissionDAO
import authentication.persistence.dao.UserDAO
import authentication.persistence.tables.PermissionTable
import authentication.persistence.tables.UserTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

/**
 * Controle para as classes de usuário e sessão de usuário
 */
class UserController {
    private val userDAO = UserDAO()

    init {
        transaction {
            SchemaUtils.create(
                UserTable,
                PermissionTable
            )
        }
    }

    /**
     * Criar um usuário do tipo cliente
     *
     * @param name          nome completo do usuário
     * @param email         email do usuário
     * @param password      senha da conta de usuário
     */
    fun createClientUser(name:String, email:String, cpf:String?, cnpj:String?, password:String):User {
        val user = User.createClient(name, email, cpf, cnpj, password)
        return userDAO.insert(user)
    }

    /**
     * Criar um usuário do tipo oficial
     *
     * @param name          nome completo do usuário
     * @param email         email do usuário
     * @param password      senha da conta de usuário
     * @param notaryId      id do cartório relacionado à conta
     */
    fun createOfficialUser(name:String, email:String, cpf:String?, password:String, notaryId: UUID):User {
        val user = User.createOfficial(name, email, cpf, password, notaryId)
        return userDAO.insert(user)
    }

    /**
     * Criar um usuário do tipo administrador de cartório
     *
     * @param name          nome completo do usuário
     * @param email         email do usuário
     * @param password      senha da conta de usuário
     * @param notaryId      id do cartório relacionado à conta
     */
    fun createManagerUser(name:String, email:String, cpf:String?, password:String, notaryId: UUID):User {
        val user = User.createManager(name, email, cpf, password, notaryId)
        return userDAO.insert(user)
    }

    /**
     * Criar um usuário do tipo administrador de sistema
     *
     * @param name          nome completo do usuário
     * @param email         email do usuário
     * @param password      senha da conta de usuário
     */
    fun createSysAdminUser(name:String, email:String, cpf:String?, password:String):User {
        val user = User.createSysAdmin(name, email, cpf, password)
        return userDAO.insert(user)
    }

    fun login(email: String, password: String):UserSession {
        TODO()
    }

    fun logout(sessionId: UUID):UserSession {
        TODO()
    }

    fun getSession(sessionId: UUID):UserSession {
        TODO()
    }
}