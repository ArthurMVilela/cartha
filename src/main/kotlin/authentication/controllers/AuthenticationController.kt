package authentication.controllers

import authentication.User
import authentication.persistence.dao.UserDAO
import authentication.persistence.tables.PermissionTable
import authentication.persistence.tables.UserTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

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

    fun setupTables() {
        transaction {
            SchemaUtils.create(
                PermissionTable,
                UserTable
            )
        }
    }
}