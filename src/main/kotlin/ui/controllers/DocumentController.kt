package ui.controllers

import document.Notary
import document.civilRegistry.birth.BirthCertificate
import document.handlers.civilRegistry.birth.CreateBirthCertificateRequest
import document.handlers.notary.CreateNotaryRequest
import document.handlers.person.CreateOfficialRequest
import document.handlers.person.CreatePhysicalPersonRequest
import document.person.Official
import document.person.PhysicalPerson
import persistence.ResultSet
import java.util.*

class DocumentController {
    private val documentUrl = System.getenv("DOCUMENT_URL")?:throw Exception()
    private val documentClient = ui.controllers.DocumentClient(documentUrl)

    suspend fun getNotaries(page:Int):ResultSet<Notary> {
        return documentClient.getNotaries(page)
    }

    suspend fun createNotary(rb: CreateNotaryRequest): Notary {
        return documentClient.createNotary(rb)
    }

    suspend fun getNotary(id: UUID): Notary {
        return documentClient.getNotary(id)
    }

    suspend fun getOfficials(notaryId: UUID): List<Official> {
        return documentClient.getOfficials(notaryId)
    }

    suspend fun createOfficial(rb: CreateOfficialRequest): Official {
        return documentClient.createOfficial(rb)
    }

    suspend fun createPhysicalPerson(rb: CreatePhysicalPersonRequest): PhysicalPerson {
        return documentClient.createPhysicalPerson(rb)
    }

    suspend fun createBirthCertificate(rb: CreateBirthCertificateRequest): BirthCertificate {
        return documentClient.createBirthCertificate(rb)
    }

    suspend fun getBirthCertificate(id: UUID): BirthCertificate {
        return documentClient.getBirthCertificate(id)
    }

    suspend fun getBirthCertificatesByOfficial(id: UUID, page: Int): ResultSet<BirthCertificate> {
        return documentClient.getBirthCertificateByOfficial(id, page)
    }

    suspend fun getBirthCertificatesByNotary(id: UUID, page: Int): ResultSet<BirthCertificate> {
        return documentClient.getBirthCertificateByNotary(id, page)
    }

    suspend fun getBirthCertificatesByCpf(cpf: String): BirthCertificate {
        return documentClient.getBirthCertificateByCpf(cpf)
    }

    suspend fun getBirthCertificatesByAffiliation(cpf: String): List<BirthCertificate> {
        return documentClient.getBirthCertificateByAffiliation(cpf)
    }
}