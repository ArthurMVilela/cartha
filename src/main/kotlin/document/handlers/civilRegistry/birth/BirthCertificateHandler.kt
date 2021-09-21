package document.handlers.civilRegistry.birth

import document.Document
import document.DocumentStatus
import document.address.Municipality
import document.civilRegistry.Affiliation
import document.civilRegistry.birth.BirthCertificate
import document.civilRegistry.birth.Grandparent
import document.controllers.BirthCertificateController
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import java.util.*

class BirthCertificateHandler {
    val birthCertificateController = BirthCertificateController()

    suspend fun createBirthCertificate(call: ApplicationCall) {
        val requestBody = try {
            call.receive<CreateBirthCertificateRequest>()
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw BadRequestException("Corpo de requesição inválido")
        }

        val id = Document.createId()

        val affiliation = mutableListOf<Affiliation>()
        requestBody.affiliation.forEach {
            affiliation.add(
                Affiliation(
                    UUID.randomUUID(),
                    it.personId,
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

        val bc = try {
            birthCertificateController.createBirthCertificate(
                BirthCertificate(
                    id,
                    DocumentStatus.WaitingValidation,
                    requestBody.officialId,
                    requestBody.notaryId,
                    null,
                    null,
                    mutableListOf(),
                    requestBody.personId,
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
            )
        } catch (ex: Exception) {
            throw ex
        }

        call.respond(HttpStatusCode.OK, bc)
    }
}