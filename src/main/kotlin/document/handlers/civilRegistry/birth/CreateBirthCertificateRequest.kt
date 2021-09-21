package document.handlers.civilRegistry.birth

import document.address.Municipality
import document.civilRegistry.birth.Twin
import document.handlers.address.CreateMunicipalityRequest
import document.handlers.civilRegistry.CreateAffiliationRequest
import document.person.Sex
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import util.serializer.LocalDateSerializer
import util.serializer.LocalDateTimeSerializer
import util.serializer.UUIDSerializer
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Serializable
class CreateBirthCertificateRequest(
    @Serializable(with = UUIDSerializer::class)
    @SerialName("official_id")
    val officialId: UUID,
    @Serializable(with = UUIDSerializer::class)
    @SerialName("notary_id")
    val notaryId: UUID,
    @Serializable(with = UUIDSerializer::class)
    @SerialName("person_id")
    val personId: UUID?,
    val name: String,
    val sex: Sex,
    @SerialName("municipality_of_birth")
    val municipalityOfBirth: CreateMunicipalityRequest,
    @SerialName("municipality_of_registry")
    val municipalityOfRegistry: CreateMunicipalityRequest,
    @SerialName("place_of_birth")
    val placeOfBirth:String,
    val affiliation: List<CreateAffiliationRequest>,
    val grandparents: List<CreateGrandparentRequest>,
    @Serializable(with = LocalDateTimeSerializer::class)
    @SerialName("date_time_of_birth")
    val dateTimeOfBirth: LocalDateTime,
    @Serializable(with = LocalDateSerializer::class)
    @SerialName("date_of_registry")
    val dateOfRegistry: LocalDate,
    @SerialName("dnn_number")
    val dnnNumber: String,
)