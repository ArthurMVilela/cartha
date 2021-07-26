package ui.controllers

import authentication.UserSession
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.http.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.request.forms.*

class AuthenticationClient {
    private val client = HttpClient(CIO) {
        install(JsonFeature)
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
    private val baseUrl = "http://localhost:8080"

    suspend fun login(email: String?, cpf: String?, cnpj: String?, password: String): UserSession {
        val response:HttpResponse = try {
            client.submitForm (
                url = baseUrl + "/login",
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
}