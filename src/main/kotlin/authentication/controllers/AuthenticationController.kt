package authentication.controllers

import authentication.User
import authentication.UserSession
import authentication.persistence.dao.UserDAO
import authentication.persistence.dao.UserSessionDAO
import authentication.persistence.tables.PermissionTable
import authentication.persistence.tables.UserSessionTable
import authentication.persistence.tables.UserTable
import io.ktor.features.*
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.IllegalArgumentException
import java.time.LocalDateTime
import java.time.ZoneOffset

class AuthenticationController {
    init {
        setupTables()
    }

    fun createClientUser(name: String, email:String, cpf:String, password: String): User {
        val user = User.createClient(name, email, cpf, password)
        return try {
            UserDAO.insert(user).toType()!!
        } catch (ex: Exception) {
            throw ex
        }
    }

    fun createOfficialUser(name: String, email:String, cpf:String, notaryId: String, password: String): User {
        val user = User.createOfficial(name, email, cpf, password, notaryId)
        return try {
            UserDAO.insert(user).toType()!!
        } catch (ex: Exception) {
            throw ex
        }
    }

    fun createManagerUser(name: String, email:String, cpf:String, notaryId: String, password: String): User {
        val user = User.createManager(name, email, cpf, password, notaryId)
        return try {
            UserDAO.insert(user).toType()!!
        } catch (ex: Exception) {
            throw ex
        }
    }

    fun createSysAdmin(name: String, email:String, cpf:String, password: String): User {
        val user = User.createSysAdmin(name, email, cpf, password)
        return try {
            UserDAO.insert(user).toType()!!
        } catch (ex: Exception) {
            throw ex
        }
    }

    fun login(email: String?, cpf: String?, password: String): UserSession {
        if (email.isNullOrEmpty() && cpf.isNullOrEmpty()){
            throw IllegalArgumentException("é necessário email ou cpf não nulo para login")
        }

        var user:User? = null
        transaction {
            user = UserDAO.selectMany(Op.build { (UserTable.email eq (email?:"")) or (UserTable.cpf eq (cpf?:"")) })
                .firstOrNull()?.toType()!!
        }
        user?: throw NotFoundException("conta de usuário não existe")

        if(!user!!.validatePassword(password)) {
            throw BadRequestException("Senha inválida")
        }


        transaction {
            val loggedSession = UserSessionDAO.selectMany(
                //
                Op.build { (UserSessionTable.userId eq user!!.id) and (UserSessionTable.end eq null)}
            )

            if(!loggedSession.empty()) {
                throw BadRequestException("Usuário já logado")
            }
        }

        val session = UserSession(user!!, LocalDateTime.now(ZoneOffset.UTC))

        return UserSessionDAO.insert(session).toType()!!
    }

    fun getSession(id:String):UserSession {
        return UserSessionDAO.select(id)?.toType()?:throw NotFoundException("sessão de usuário não existe")
    }

    fun logout(id: String){
        val session = UserSessionDAO.select(id)?.toType()?:throw NotFoundException("sessão de usuário não existe")
        if (session.end != null) {
            throw BadRequestException("Sessão de usuário já foi terminada")
        }
        session.endSession(LocalDateTime.now(ZoneOffset.UTC))
        UserSessionDAO.update(session)
    }

    fun setupTables() {
        transaction {
            SchemaUtils.create(
                PermissionTable,
                UserTable,
                UserSessionTable
            )
        }
    }
}