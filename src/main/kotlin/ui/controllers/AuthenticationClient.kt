package ui.controllers

import authentication.Role
import authentication.User
import authentication.UserSession
import authentication.handlers.AccessLogRequestBody
import authentication.logging.AccessLog
import authentication.logging.AccessLogSearchFilter
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.http.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.forms.*
import persistence.ResultSet
import java.util.*

class AuthenticationClient {
    val authenticationURL = System.getenv("AUTHENTICATION_URL")?:throw Exception()

    private val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        expectSuccess = false
        HttpResponseValidator {
            validateResponse { response ->
                val statusCode = response.status.value
                when (statusCode) {
                    in 300..399 -> throw RedirectResponseException(response)
                    in 400..499 -> throw ClientRequestException(response)
                    in 500..599 -> throw ServerResponseException(response)
                }
            }
        }
    }

    suspend fun login(email: String?, cpf: String?, cnpj: String?, password: String): UserSession {
        val response:HttpResponse = try {
            client.submitForm (
                url = "$authenticationURL/login",
                formParameters = Parameters.build {
                    if (email != null) append("email", email)
                    if (cpf != null) append("cpf", cpf)
                    if (cnpj != null) append("cnpj", cnpj)
                    append("password", password)
                }
            )
        } catch (ex: Exception) {
            throw ex
        }
        return response.receive<UserSession>()
    }

    suspend fun createAccount(
        name: String,
        role: Role,
        email: String,
        cpf: String?,
        cnpj: String?,
        password: String,
        notaryId: UUID?
    ):User {
        val response:HttpResponse = try {
            client.submitForm (
                url = "$authenticationURL/users",
                formParameters = Parameters.build {
                    append("name", name)
                    append("role", role.name)
                    append("email", email)
                    append("password", password)
                    if(notaryId != null) append("notary_id", notaryId.toString())
                    if(cpf != null) append("cpf", cpf)
                    if(cnpj != null) append("cnpj", cnpj)
                }
            )
        } catch (ex: Exception) {
            throw ex
        }

        return response.receive()
    }

    suspend fun logout(sessionId: UUID) : UserSession {
        val response:HttpResponse = try {
            client.post("$authenticationURL/logout/${sessionId.toString()}")
        } catch (ex: Exception) {
            throw ex
        }

        return response.receive<UserSession>()
    }

    suspend fun getSession(id: UUID):UserSession? {
        val response:HttpResponse = try {
            client.get("$authenticationURL/session/${id.toString()}")
        } catch (ex: Exception) {
            throw ex
        }

        return response.receive<UserSession>()
    }

    suspend fun getAccessLogs(filter: AccessLogSearchFilter, page: Int) : ResultSet<AccessLog> {
        return try {
            client.get<ResultSet<AccessLog>>("$authenticationURL/access_logs") {
                parameter("page", page)
                contentType(ContentType.Application.Json)
                body = filter
            }
        } catch (ex: Exception) {
            throw ex
        }
    }

    suspend fun getAccessLog(id:UUID) : AccessLog {
        return try {
            client.get<AccessLog>("$authenticationURL/access_logs/$id") {

            }
        } catch (ex: Exception) {
            throw ex
        }
    }

    suspend fun logAction(request: AccessLogRequestBody) {
        client.post<AccessLog>{
            url("$authenticationURL/access_logs")
            contentType(ContentType.Application.Json)
            body = request
        }
    }

    suspend fun getUserAccount(email: String): User {
        val response: HttpResponse = try {
            client.get("$authenticationURL/user/email/$email") {
            }
        }catch (ex: Exception) {
            throw ex
        }

        return response.receive()
    }
}