package ui.controllers

import document.Notary
import document.handlers.notary.CreateNotaryRequest
import document.handlers.person.CreateOfficialRequest
import document.handlers.person.CreatePhysicalPersonRequest
import document.person.Official
import document.person.PhysicalPerson
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.get
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.features.*
import io.ktor.http.*
import persistence.ResultSet
import java.util.*

class DocumentClient(
    val documentUrl: String
) {
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

    suspend fun getNotaries(page:Int = 1): ResultSet<Notary> {
        val response: HttpResponse = try {
            client.get("$documentUrl/notary") {
                parameter("page", page)
            }
        } catch (ex: Exception) {
            throw ex
        }

        println(response.status)

        if (response.status == HttpStatusCode.NotFound) {
            throw NotFoundException("Página não encontrada")
        }

        return response.receive()
    }

    suspend fun createNotary(rb: CreateNotaryRequest): Notary {
        val response: HttpResponse = try {
            client.post("$documentUrl/notary") {
                contentType(ContentType.Application.Json)
                body = rb
            }
        } catch (ex: Exception) {
            throw ex
        }

        return response.receive()
    }

    suspend fun getNotary(id: UUID): Notary {
        val response:HttpResponse = try {
            client.get("$documentUrl/notary/$id") {

            }
        } catch (ex: Exception) {
            throw ex
        }

        return response.receive()
    }

    suspend fun getOfficials(id: UUID): List<Official> {
        val response:HttpResponse = try {
            client.get("$documentUrl/person/official/notary/$id") {}
        } catch (ex: Exception) {
            throw ex
        }

        return response.receive()
    }

    suspend fun createOfficial(rb: CreateOfficialRequest): Official {
        val response:HttpResponse = try {
            client.post("$documentUrl/person/official") {
                contentType(ContentType.Application.Json)
                body = rb
            }
        } catch (ex: Exception) {
            throw ex
        }

        return response.receive()
    }

    suspend fun createPhysicalPerson(rb: CreatePhysicalPersonRequest): PhysicalPerson {
        val response:HttpResponse = try {
            client.post("$documentUrl/person/physical_person") {
                contentType(ContentType.Application.Json)
                body = rb
            }
        }catch (ex: Exception) {
            throw ex
        }

        return response.receive()
    }
}