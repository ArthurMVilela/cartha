package authentication.controllers

import authentication.Role
import authentication.User
import authentication.logging.controllers.AccessLogController
import java.util.*

/**
 * Façade para os controles do pacore de autentificação
 */
class AuthenticationController {
    private val accessLogController = AccessLogController()
    private val userController = UserController()

    /**
     * Cria uma conta de usuário
     *
     * @param role              Função/Cargo do usuário
     * @param name              Nome do usuário
     * @param email             Email do usuário
     * @param password          Senha da conta de usuário
     * @param notaryId          Id do cartório
     */
    fun createUser(role: Role, name:String, email:String, password:String, notaryId: UUID?): User {
        return when(role) {
            Role.Client -> userController.createClientUser(name, email, password)
            Role.SysAdmin -> userController.createSysAdminUser(name, email, password)
            Role.Official -> {
                if (notaryId == null) {
                    throw NullPointerException("Id do cartório não pode ser nulo para criar uma conta de oficial.")
                } else {
                    userController.createOfficialUser(name, email, password, notaryId)
                }
            }
            Role.Manager -> {
                if (notaryId == null) {
                    throw NullPointerException("Id do cartório não pode ser nulo para criar uma conta de oficial.")
                } else {
                    userController.createManagerUser(name, email, password, notaryId)
                }
            }
        }
    }
}