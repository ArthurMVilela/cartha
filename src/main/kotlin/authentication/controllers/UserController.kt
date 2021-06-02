package authentication.controllers

import authentication.*
import authentication.exception.InvalidCredentialsException
import authentication.exception.UserSessionNotFound
import authentication.persistence.dao.PermissionDAO
import authentication.persistence.dao.UserDAO
import authentication.persistence.dao.UserSessionDAO
import authentication.persistence.tables.PermissionTable
import authentication.persistence.tables.UserSessionTable
import authentication.persistence.tables.UserTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.util.*

/**
 * Controle para as classes de usuário e sessão de usuário
 */
class UserController {
    private val userDAO = UserDAO()
    private val userSessionDAO = UserSessionDAO()

    init {
        transaction {
            SchemaUtils.create(
                UserTable,
                PermissionTable,
                UserSessionTable,
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

    fun login(email: String?, cpf: String?, cnpj: String?, password: String):UserSession {
        if ((email.isNullOrEmpty() && cpf.isNullOrEmpty() && cnpj.isNullOrEmpty()) || (!email.isNullOrEmpty() && !cpf.isNullOrEmpty() && !cnpj.isNullOrEmpty())) {
            throw InvalidCredentialsException("Deve haver somente email ou cpf no login, não os dois.")
        }

        val user = when {
            email != null -> userDAO.selectMany(Op.build { UserTable.email eq email }).firstOrNull()
            cpf != null -> userDAO.selectMany(Op.build { UserTable.cpf eq cpf }).firstOrNull()
            cnpj != null -> userDAO.selectMany(Op.build { UserTable.cnpj eq cnpj }).firstOrNull()
            else -> throw InvalidCredentialsException("Deve haver somente email ou cpf no login, não os dois.")
        }?: throw InvalidCredentialsException("Usuário não existe.")

        when(user.status) {
            UserStatus.Online -> throw InvalidCredentialsException("Usuário já está logado.")
            UserStatus.Deactivated -> throw InvalidCredentialsException("Conta de usuário desativada.")
        }

        if (!user.validatePassword(password)) {
            throw InvalidCredentialsException("Senha inválida")
        }

        val session = UserSession(user, LocalDateTime.now())

        userDAO.update(user)
        return userSessionDAO.insert(session)
    }

    fun logout(sessionId: UUID):UserSession {
        val session = getSession(sessionId)
        session.endSession(LocalDateTime.now())
        userSessionDAO.update(session)
        userDAO.update(session.user)
        return session
    }

    fun getSession(sessionId: UUID):UserSession {
        val session = userSessionDAO.select(sessionId)?:throw UserSessionNotFound("Sessão de usuário não encontrada.")
        return session
    }
}