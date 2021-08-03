package ui.controllers

import authentication.UserSession
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
import newPersistence.ResultSet
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
}