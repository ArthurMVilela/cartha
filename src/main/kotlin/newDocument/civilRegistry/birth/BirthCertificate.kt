package newDocument.civilRegistry.birth

import newDocument.civilRegistry.CivilRegistryDocument
import newDocument.civilRegistry.Registering
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import newDocument.DocumentStatus
import newDocument.address.Municipality
import newDocument.civilRegistry.Affiliation
import newDocument.person.Sex
import util.serializer.LocalDateTimeSerializer
import util.serializer.UUIDSerializer
import java.time.LocalDateTime
import java.util.*

@Serializable
class BirthCertificate(
    @Serializable(with = UUIDSerializer::class)
    override val id: UUID,
    override var status: DocumentStatus,
    @SerialName("official_id")
    override val officialId: String,
    @SerialName("notary_id")
    override val notaryId: String,
    override var hash: String?,
    @SerialName("registration_number")
    override var registrationNumber:String?,
    override val registering: MutableList<Registering>,

    @SerialName("municipality_of_birth")
    val municipalityOfBirth: Municipality,
    @SerialName("municipality_of_registry")
    val municipalityOfRegistry: Municipality,

    @SerialName("place_of_birth")
    val placeOfBirth:String,

    @Serializable(with = LocalDateTimeSerializer::class)
    @SerialName("date_time_of_birth")
    val dateTimeOfBirth: LocalDateTime,
    val twins:MutableSet<Twin>,

    @SerialName("dnn_number")
    val dnnNumber: String,

    val affiliation: List<Affiliation>,
    val grandparents: List<Grandparent>,

    val name: String,
    val sex: Sex

):CivilRegistryDocument() {
    override fun createHash(): String {
        return ""
    }
}