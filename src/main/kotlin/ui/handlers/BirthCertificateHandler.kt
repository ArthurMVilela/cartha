package ui.handlers

import authentication.Role
import authentication.Subject
import authentication.logging.ActionType
import blockchain.TransactionType
import blockchain.handlers.CreateTransactionRequest
import document.address.UF
import document.civilRegistry.Affiliation
import document.civilRegistry.birth.GrandparentType
import document.controllers.NotaryController
import document.handlers.address.CreateMunicipalityRequest
import document.handlers.civilRegistry.CreateAffiliationRequest
import document.handlers.civilRegistry.birth.CreateBirthCertificateRequest
import document.handlers.civilRegistry.birth.CreateGrandparentRequest
import document.person.Sex
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import serviceExceptions.BadRequestException
import ui.controllers.BlockchainController
import ui.controllers.DocumentController
import ui.features.getUserId
import ui.features.getUserPermissions
import ui.features.getUserRole
import ui.features.logAction
import ui.pages.document.civilRegistry.BirthCertificatePage
import ui.pages.document.civilRegistry.BirthCertificatePrintPage
import ui.pages.document.civilRegistry.BirthCertificatesPageBuilder
import ui.pages.document.civilRegistry.CreateBirthCertificatePageBuilder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month
import java.util.*

class BirthCertificateHandler {
    private val documentController = DocumentController()
    private val blockchainController = BlockchainController()

    suspend fun getBirthCertificatePage(call: ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw io.ktor.features.BadRequestException("Id inválida.")
        }

        val bc = try {
            documentController.getBirthCertificate(id)
        } catch (ex: Exception) {
            throw NotFoundException("Não encontrada.")
        }

        val pageBuilder = BirthCertificatePage()
        pageBuilder.setupMenu(call.getUserRole())
        pageBuilder.setBirthCertificate(bc)

        call.logAction(ActionType.SeeBirthCertificate, Subject.CivilRegistry, bc.id)

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    suspend fun getBirthCertificatePrintPage(call: ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw io.ktor.features.BadRequestException("Id inválida.")
        }

        val bc = try {
            documentController.getBirthCertificate(id)
        } catch (ex: Exception) {
            throw NotFoundException("Não encontrada.")
        }

        val notary = try {
            documentController.getNotary(bc.notaryId)
        } catch (ex: Exception) {
            throw ex
        }

        val official = try {
            documentController.getOfficials(bc.notaryId).first { x -> x.id == bc.officialId}
        } catch (ex: Exception) {
            throw ex
        }

        val pageBuilder = BirthCertificatePrintPage()
        pageBuilder.setBirthCertificate(bc)
        pageBuilder.setNotary(notary)
        pageBuilder.setOfficial(official)

        call.logAction(ActionType.PrintBirthCertificates, Subject.CivilRegistry, bc.id)

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    suspend fun getCreateBirthCertificatePage(call: ApplicationCall) {
        val userId = call.getUserId()
        val userRole = call.getUserRole()
        val notaryId = if (userRole == Role.Manager) {
            call.getUserPermissions()
                .first { it.subject == Subject.Notary && it.domainId != null }.domainId!!
        } else {
            call.getUserPermissions()
                .first { it.subject == Subject.CivilRegistry && it.domainId != null }.domainId!!
        }

        val officialId = documentController.getOfficials(notaryId).first { it.accountId == userId }.id

        val pageBuilder = CreateBirthCertificatePageBuilder()

        pageBuilder.setupMenu(call.getUserRole())
        pageBuilder.setNotaryId(notaryId)
        pageBuilder.setOfficialId(officialId)

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    suspend fun getBirthCertificateByOfficialPage(call: ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw io.ktor.features.BadRequestException("Id inválida.")
        }

        val pageNumber = try {
            call.request.queryParameters["page"]?.toInt()?:1
        } catch (ex: Exception) {
            throw BadRequestException("Página inválida")
        }

        if (pageNumber < 1) {
            throw BadRequestException("Página inválida")
        }

        val bcs = documentController.getBirthCertificatesByOfficial(id, pageNumber)

        val pageBuilder = BirthCertificatesPageBuilder()

        pageBuilder.setupMenu(call.getUserRole())
        pageBuilder.setResultSet(bcs)

        call.logAction(ActionType.SeeBirthCertificates, Subject.CivilRegistry, null)

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    suspend fun getBirthCertificateByNotaryPage(call: ApplicationCall) {
        val id = try {
            UUID.fromString(call.parameters["id"])
        } catch (ex: Exception) {
            throw io.ktor.features.BadRequestException("Id inválida.")
        }

        val pageNumber = try {
            call.request.queryParameters["page"]?.toInt()?:1
        } catch (ex: Exception) {
            throw BadRequestException("Página inválida")
        }

        if (pageNumber < 1) {
            throw BadRequestException("Página inválida")
        }

        val bcs = documentController.getBirthCertificatesByNotary(id, pageNumber)

        val pageBuilder = BirthCertificatesPageBuilder()

        pageBuilder.setupMenu(call.getUserRole())
        pageBuilder.setResultSet(bcs)

        call.logAction(ActionType.SeeBirthCertificates, Subject.CivilRegistry, null)

        val page = pageBuilder.build()
        call.respond(HttpStatusCode.OK, FreeMarkerContent(page.template, page.data))
    }

    suspend fun createBirthCertificate(call: ApplicationCall) {
        val form = call.receiveParameters()

        val rb = parseCreateBirthCertificateForm(form)

        val cb = documentController.createBirthCertificate(rb)

        val transaction = blockchainController.createTransaction(
            CreateTransactionRequest(
                cb.id, cb.hash!!, TransactionType.Creation
            )
        )

        call.logAction(ActionType.CreateBirthCertificates, Subject.CivilRegistry, cb.id)

        call.respondRedirect("/civil-registry/birth/${cb.id}")
    }

    private fun parseCreateBirthCertificateForm(form: Parameters):CreateBirthCertificateRequest {
        val officialId = UUID.fromString(form["official-id"]!!)
        val notaryId = UUID.fromString(form["notary-id"]!!)
        val personId = null
        val cpf = form["cpf"]!!
        val name = form["name"]!!
        val sex = Sex.valueOf(form["sex"]!!)
        val municipalityOfBirth = CreateMunicipalityRequest(
            form["municipality-of-birth-name"]!!,
            UF.valueOf(form["municipality-of-birth-uf"]!!)
        )
        val municipalityOfRegistry = CreateMunicipalityRequest(
            form["municipality-of-registry-name"]!!,
            UF.valueOf(form["municipality-of-registry-uf"]!!)
        )
        val placeOfBirth = form["place-of-birth"]!!
        val affiliation = parseAffiliationForms(form)
        val grandparents = parseGrandparentForms(form)
        val dateTimeOfBirth = LocalDateTime.of(
            LocalDate.of(
                form["birthday-year"]!!.toInt(),
                Month.valueOf(form["birthday-month"]!!),
                form["birthday-day"]!!.toInt(),
            ),
            LocalTime.of(
                form["birth-time-h"]!!.toInt(),
                form["birth-time-m"]!!.toInt()
            )
        )
        val dateOfRegistry = LocalDate.of(
            form["registry-year"]!!.toInt(),
            Month.valueOf(form["registry-month"]!!),
            form["registry-day"]!!.toInt(),
        )
        val dnnNumber = form["dn-number"]!!

        return CreateBirthCertificateRequest(
            officialId, notaryId, personId, cpf, name, sex, municipalityOfBirth, municipalityOfRegistry,
            placeOfBirth, affiliation, grandparents, dateTimeOfBirth, dateOfRegistry, dnnNumber
        )
    }

    private fun parseAffiliationForms(form: Parameters): List<CreateAffiliationRequest> {
        val affiliation = mutableListOf<CreateAffiliationRequest>()
        val len = form["affiliation-length"]!!.toInt()

        for (i in 0 until len) {
            affiliation.add(
                CreateAffiliationRequest(
                    null,
                    form["affiliation[$i]cpf"]!!,
                    form["affiliation[$i]name"]!!,
                    CreateMunicipalityRequest(
                        form["affiliation[$i]municipality-name"]!!,
                        UF.valueOf(form["affiliation[$i]municipality-uf"]!!,)
                    )
                )
            )
        }

        return affiliation
    }

    private fun parseGrandparentForms(form: Parameters): List<CreateGrandparentRequest> {
        val grandparents = mutableListOf<CreateGrandparentRequest>()
        val len = form["grandparent-length"]!!.toInt()

        for (i in 0 until len) {
            grandparents.add(
                CreateGrandparentRequest(
                    null,
                    form["grandparent[$i]cpf"]!!,
                    form["grandparent[$i]name"]!!,
                    GrandparentType.valueOf(form["grandparent[$i]type"]!!,),
                    CreateMunicipalityRequest(
                        form["grandparent[$i]municipality-name"]!!,
                        UF.valueOf(form["grandparent[$i]municipality-uf"]!!,)
                    )
                )
            )
        }

        return grandparents
    }


}