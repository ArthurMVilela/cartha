package document.civilRegistry

import document.*
import kotlinx.serialization.Serializable
import util.serializer.LocalDateTimeSerializer
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period

/**
 * Representa uma certidão de óbito de um indivíduo.
 *
 * @property personId                   ID da pessoa relacionada a esse documento
 * @property sex                        Sexo do indivíduo
 * @property color                      Cor/Raça do indivíduo
 * @property civilStatus                Estado cívil do indivíduo
 * @property age                        Idade do indivíduo
 *
 * @property birthPlace                 Local de nascimento do indivíduo
 * @property documentOfIdentity         Documento de identidade do indvíduo
 * @property affiliation                Filiação do indivíduo
 * @property residency                  Endereço de residencia do indivído
 *
 * @property dateTimeOfDeath            Data e hora da morte
 * @property placeOfDeath               Local da morte
 * @property causeOfDeath               Causa da morte
 * @property burialOrCremationLocation  Local de sepultura ou cremação
 * @property documentDeclaringDeath     Nome e número do documento declarando a morte
 */
@Serializable
class DeathCertificate(
    override val id: String?,
    override val status: DocumentStatus,
    override val officialId: String,
    override val notaryId: String,

    override val registration:String,
    override val observationsAndRegistering:List<String>,

    val personId: String,
    val sex: Sex,
    val color: Color,
    val civilStatus: CivilStatus,
    val age: Int,

    val birthPlace: String,
    val documentOfIdentity: String,
    val affiliation: Affiliation,
    val residency:String,

    @Serializable(with = LocalDateTimeSerializer::class)
    val dateTimeOfDeath:LocalDateTime,
    val placeOfDeath:String,
    val causeOfDeath:String,
    val burialOrCremationLocation:String?,
    val documentDeclaringDeath: String,
):CivilRegistryDocument() {
    constructor(
        id: String?,
        status: DocumentStatus,
        officialId: String,
        notaryId: String,
        registration: String,
        observationsAndRegistering:List<String>,

        person: PhysicalPerson,

        birthPlace: String,
        documentOfIdentity: String,
        affiliation: Affiliation,
        residency:String,

        dateTimeOfDeath:LocalDateTime,
        placeOfDeath:String,
        causeOfDeath:String,
        burialOrCremationLocation:String?,
        documentDeclaringDeath: String,
    ):this(
        id, status, officialId, notaryId, registration, observationsAndRegistering, person.id!!, person.sex,
        person.color, person.civilStatus, Period.between(person.birthDay, LocalDate.now()).years, birthPlace,
        documentOfIdentity, affiliation, residency, dateTimeOfDeath, placeOfDeath, causeOfDeath,
        burialOrCremationLocation, documentDeclaringDeath
    )
}