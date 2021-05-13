package authentication.controllers

import authentication.User
import authentication.UserSession
import java.util.*

/**
 * Controle para as classes de usuário e sessão de usuário
 */
class UserController {
    fun createClientUser(name:String, email:String, password:String):User {
        TODO()
    }
    fun createOfficialUser(name:String, email:String, password:String):User {
        TODO()
    }
    fun createManagerUser(name:String, email:String, password:String):User {
        TODO()
    }
    fun createSysAdminUser(name:String, email:String, password:String):User {
        TODO()
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