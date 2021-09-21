package document.civilRegistry.birth

import document.civilRegistry.CivilRegistryDocument
import document.civilRegistry.Registering
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import document.DocumentStatus
import document.address.Municipality
import document.civilRegistry.Affiliation
import document.person.Sex
import util.serializer.LocalDateSerializer
import util.serializer.LocalDateTimeSerializer
import util.serializer.UUIDSerializer
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Serializable
class BirthCertificate(
    @Serializable(with = UUIDSerializer::class)
    override val id: UUID,
    override var status: DocumentStatus,
    @Serializable(with = UUIDSerializer::class)
    @SerialName("official_id")
    override val officialId: UUID,
    @Serializable(with = UUIDSerializer::class)
    @SerialName("notary_id")
    override val notaryId: UUID,
    override var hash: String?,
    @SerialName("registration_number")
    override var registrationNumber:String?,
    override val registering: MutableList<Registering>,

    @Serializable(with = UUIDSerializer::class)
    @SerialName("person_id")
    val personId: UUID,
    val name: String,
    val sex: Sex,
    @SerialName("municipality_of_birth")
    val municipalityOfBirth: Municipality,
    @SerialName("municipality_of_registry")
    val municipalityOfRegistry: Municipality,
    @SerialName("place_of_birth")
    val placeOfBirth:String,
    val affiliation: List<Affiliation>,
    val grandparents: List<Grandparent>,
    @Serializable(with = LocalDateTimeSerializer::class)
    @SerialName("date_time_of_birth")
    val dateTimeOfBirth: LocalDateTime,
    @Serializable(with = LocalDateSerializer::class)
    @SerialName("date_of_registry")
    val dateOfRegistry: LocalDate,
    val twins:MutableSet<Twin>,
    @SerialName("dnn_number")
    val dnnNumber: String,
):CivilRegistryDocument() {
    override fun createHash(): String {
        return ""
    }
}