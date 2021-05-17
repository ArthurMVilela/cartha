package authentication.controllers

import authentication.Permission
import authentication.Role
import authentication.User
import authentication.UserSession
import java.util.*

/**
 * Controle para as classes de usuário e sessão de usuário
 */
class UserController {
    /**
     * Criar um usuário do tipo cliente
     *
     * @param name          nome completo do usuário
     * @param email         email do usuário
     * @param password      senha da conta de usuário
     */
    fun createClientUser(name:String, email:String, password:String):User {
        return User.createClient(name, email, password)
    }

    /**
     * Criar um usuário do tipo oficial
     *
     * @param name          nome completo do usuário
     * @param email         email do usuário
     * @param password      senha da conta de usuário
     * @param notaryId      id do cartório relacionado à conta
     */
    fun createOfficialUser(name:String, email:String, password:String, notaryId: UUID):User {
        return User.createOfficial(name, email, password, notaryId)
    }

    /**
     * Criar um usuário do tipo administrador de cartório
     *
     * @param name          nome completo do usuário
     * @param email         email do usuário
     * @param password      senha da conta de usuário
     * @param notaryId      id do cartório relacionado à conta
     */
    fun createManagerUser(name:String, email:String, password:String, notaryId: UUID):User {
        return User.createManager(name, email, password, notaryId)
    }

    /**
     * Criar um usuário do tipo administrador de sistema
     *
     * @param name          nome completo do usuário
     * @param email         email do usuário
     * @param password      senha da conta de usuário
     */
    fun createSysAdminUser(name:String, email:String, password:String):User {
        return User.createSysAdmin(name, email, password)
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