package document.handlers.civilRegistry.birth

import document.Document
import document.DocumentStatus
import document.address.Municipality
import document.civilRegistry.Affiliation
import document.civilRegistry.RegistryBookType
import document.civilRegistry.StorageCode
import document.civilRegistry.birth.BirthCertificate
import document.civilRegistry.birth.Grandparent
import document.controllers.BirthCertificateController
import document.controllers.CivilRegistryDocumentController
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import java.util.*

class BirthCertificateHandler {
    private val birthCertificateController = BirthCertificateController()
    private val civilRegistryController = CivilRegistryDocumentController()

    suspend fun createBirthCertificate(call: ApplicationCall) {
        val requestBody = try {
            call.receive<CreateBirthCertificateRequest>()
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw BadRequestException("Corpo de requesição inválido")
        }

        val bc = try {
            birthCertificateController.createBirthCertificate(
                buildBirthCertificate(requestBody)
            )
        } catch (ex: Exception) {
            throw ex
        }

        call.respond(HttpStatusCode.OK, bc)
    }

    suspend fun getBirthCertificate(call: ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw serviceExceptions.BadRequestException("id não válida")
        }

        val bc = birthCertificateController.getBirthCertificate(id)?:throw NotFoundException("Certidão de nascimento não encontrada")

        call.respond(HttpStatusCode.OK, bc)
    }

    suspend fun getBirthCertificatesByOfficial(call: ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw serviceExceptions.BadRequestException("id não válida")
        }

        val page = try {
            call.request.queryParameters["page"]?.toInt()?:1
        } catch (ex: Exception) {
            throw serviceExceptions.BadRequestException("Página inválida")
        }

        if (page < 1) {
            throw serviceExceptions.BadRequestException("Página inválida")
        }

        val bc = birthCertificateController.getBirthCertificatesByOfficial(id, page)

        call.respond(HttpStatusCode.OK, bc)
    }

    suspend fun getBirthCertificatesByNotary(call: ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw serviceExceptions.BadRequestException("id não válida")
        }

        val page = try {
            call.request.queryParameters["page"]?.toInt()?:1
        } catch (ex: Exception) {
            throw serviceExceptions.BadRequestException("Página inválida")
        }

        if (page < 1) {
            throw serviceExceptions.BadRequestException("Página inválida")
        }

        val bc = birthCertificateController.getBirthCertificatesByNotary(id, page)

        call.respond(HttpStatusCode.OK, bc)
    }

    suspend fun getBirthCertificateByCpf(call: ApplicationCall) {
        val cpf = call.parameters["cpf"]!!

        val bc = birthCertificateController.getBirthCertificate(cpf)?:throw NotFoundException("Certidão não Encontrada")

        call.respond(HttpStatusCode.OK, bc)
    }

    suspend fun getBirthCertificatesWithAffiliation(call: ApplicationCall) {
        val cpf = call.parameters["cpf"]!!

        val bc = birthCertificateController.getBirthCertificatesWithAffiliation(cpf)

        call.respond(HttpStatusCode.OK, bc)
    }

    private fun buildBirthCertificate(requestBody: CreateBirthCertificateRequest):BirthCertificate {
        val id = Document.createId()

        val registrationNumber = civilRegistryController.createRegistrationNumber(
            requestBody.notaryId,
            StorageCode.IncorporatedStorage,
            RegistryBookType.A,
            requestBody.dateOfRegistry,
            1,
            1
        )

        val affiliation = mutableListOf<Affiliation>()
        requestBody.affiliation.forEach {
            affiliation.add(
                Affiliation(
                    UUID.randomUUID(),
                    it.personId,
                    it.cpf,
                    id,
                    it.name,
                    Municipality(
                        UUID.randomUUID(),
                        it.municipality.name,
                        it.municipality.uf
                    )
                )
            )
        }

        val grandparents = mutableListOf<Grandparent>()
        requestBody.grandparents.forEach {
            grandparents.add(
                Grandparent(
                    UUID.randomUUID(),
                    it.personId,
                    it.cpf,
                    id,
                    it.name,
                    it.type,
                    Municipality(
                        UUID.randomUUID(),
                        it.municipality.name,
                        it.municipality.uf
                    )
                )
            )
        }

        return BirthCertificate(
            id,
            DocumentStatus.WaitingValidation,
            requestBody.officialId,
            requestBody.notaryId,
            null,
            registrationNumber,
            mutableListOf(),
            requestBody.personId,
            requestBody.cpf,
            requestBody.name,
            requestBody.sex,
            Municipality(
                UUID.randomUUID(),
                requestBody.municipalityOfBirth.name,
                requestBody.municipalityOfBirth.uf
            ),
            Municipality(
                UUID.randomUUID(),
                requestBody.municipalityOfRegistry.name,
                requestBody.municipalityOfRegistry.uf
            ),
            requestBody.placeOfBirth,
            affiliation,
            grandparents,
            requestBody.dateTimeOfBirth,
            requestBody.dateOfRegistry,
            mutableSetOf(),
            requestBody.dnnNumber
        )
    }
}