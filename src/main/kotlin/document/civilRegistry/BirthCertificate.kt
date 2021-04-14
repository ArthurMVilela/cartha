package document.civilRegistry

import document.*
import kotlinx.serialization.Serializable
import util.serializer.LocalDateSerializer
import util.serializer.LocalTimeSerializer
import java.time.LocalDate
import java.time.LocalTime

/**
 * Representa uma certidão de casamento
 *
 * @property timeOfBirth                Hora do nascimento
 * @property municipalityOfBirth        Município em que o nascimento ocorreu
 * @property UFOfBirth                  UF em que o nascimento ocorreu
 * @property municipalityOfRegistry     Município em que o registro ocorreu
 * @property UFOfRegistry               UF em que o registro ocorreu
 * @property affiliations               Filiações
 * @property grandParents               Avós
 * @property twin                       Se a matricula é de um gêmeo
 * @property twins                      Registro de gêmeos
 * @property dateOfRegistry             Data do registro
 * @property DNNumber                   Número da Declaração de Nascido Vivo
 * @property observations               Observações
 *
 * @property personId                   Id da pessoa relacionada a esse documento
 * @property cpf                        cpf da pessoa relacionada a esse documento
 * @property name                       Nome
 * @property dateOfBirth                Data do nascimento
 * @property sex                        Sexo
 */
@Serializable
class BirthCertificate(
    override val id: String?,
    override val status: DocumentStatus,
    override val officialId: String,
    override val notaryId: String,
    override val registration: String,
    override val observationsAndRegistering:List<String>,

    @Serializable(with= LocalTimeSerializer::class)
    val timeOfBirth: LocalTime,
    val municipalityOfBirth: String,
    val UFOfBirth: UF,
    val municipalityOfRegistry: String,
    val UFOfRegistry: UF,
    val affiliations: List<Affiliation>,
    val grandParents: List<Grandparent>,
    val twin: Boolean,
    val twins: List<Twin>?,
    @Serializable(with = LocalDateSerializer::class)
    val dateOfRegistry: LocalDate,
    val DNNumber: String,

    val personId: String,
    val cpf: String,
    val name: String,
    @Serializable(with = LocalDateSerializer::class)
    val dateOfBirth: LocalDate,
    val sex: Sex,
):CivilRegistryDocument() {
    constructor(
        id: String?,
        status: DocumentStatus,
        officialId: String,
        notaryId: String,
        registration: String,
        observationsAndRegistering:List<String>,
        timeOfBirth: LocalTime,
        municipalityOfBirth: String,
        UFOfBirth: UF,
        municipalityOfRegistry: String,
        UFOfRegistry: UF,
        affiliations: List<Affiliation>,
        grandParents: List<Grandparent>,
        twin: Boolean,
        twins: List<Twin>?,
        dateOfRegistry: LocalDate,
        DNNumber: String,

        person: PhysicalPerson
    ):this(
        id, status, officialId, notaryId, registration, observationsAndRegistering, timeOfBirth, municipalityOfBirth,
        UFOfBirth, municipalityOfRegistry, UFOfRegistry, affiliations, grandParents, twin, twins,
        dateOfRegistry, DNNumber, person.id!!, person.cpf, person.name, person.birthday, person.sex
    )
}