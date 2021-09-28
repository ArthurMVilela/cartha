package document.handlers.civilRegistry.marriage

import document.Document
import document.DocumentStatus
import document.address.Municipality
import document.civilRegistry.Affiliation
import document.civilRegistry.marriage.MarriageCertificate
import document.civilRegistry.marriage.Spouse
import document.controllers.MarriageCertificateController
import document.handlers.civilRegistry.birth.CreateBirthCertificateRequest
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import java.util.*

class MarriageCertificateHandler {
    private val controller = MarriageCertificateController()

    suspend fun createMarriageCertificate(call: ApplicationCall) {
        val requestBody = try {
            call.receive<CreateMarriageCertificateRequest>()
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw BadRequestException("Corpo de requesição inválido")
        }

        val mc = try {
            controller.createMarriageCertificate(
                buildMarriageCertificate(requestBody)
            )
        } catch (ex: Exception) {
            throw ex
        }

        call.respond(HttpStatusCode.OK, mc)
    }

    suspend fun getMarriageCertificate(call: ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw serviceExceptions.BadRequestException("id não válida")
        }

        val mc = controller.getMarriageCertificate(id)?:throw NotFoundException("Certidão de casamento não encontrada")

        call.respond(HttpStatusCode.OK, mc)
    }

    private fun buildMarriageCertificate(requestBody: CreateMarriageCertificateRequest): MarriageCertificate {
        val id = Document.createId()

        val spouses = mutableListOf<Spouse>()

        requestBody.spouses.forEach {
            val affiliation = mutableListOf<Affiliation>()

            it.affiliation.forEach { aff ->
                affiliation.add(
                    Affiliation(
                        UUID.randomUUID(),
                        aff.personId,
                        aff.cpf,
                        id,
                        aff.name,
                        Municipality(
                            UUID.randomUUID(),
                            aff.municipality.name,
                            aff.municipality.uf
                        )
                    )
                )
            }

            spouses.add(
                Spouse(
                    UUID.randomUUID(),
                    id,
                    it.personId,
                    it.singleName,
                    it.marriedName,
                    it.birthday,
                    it.nationality,
                    affiliation
                )
            )

        }

        return MarriageCertificate(
            id,
            DocumentStatus.WaitingValidation,
            requestBody.officialId,
            requestBody.notaryId,
            null,
            null,
            mutableListOf(),
            spouses,
            requestBody.dateOfRegistry,
            requestBody.matrimonialRegime
        )
    }
}