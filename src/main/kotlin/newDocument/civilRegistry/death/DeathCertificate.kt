package newDocument.civilRegistry.death

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import newDocument.DocumentStatus
import newDocument.address.Address
import newDocument.address.Municipality
import newDocument.civilRegistry.Affiliation
import newDocument.civilRegistry.CivilRegistryDocument
import newDocument.civilRegistry.IdentityDocumentType
import newDocument.civilRegistry.Registering
import newDocument.person.CivilStatus
import newDocument.person.Color
import newDocument.person.Sex
import util.serializer.LocalDateSerializer
import util.serializer.LocalDateTimeSerializer
import util.serializer.UUIDSerializer
import java.time.LocalDateTime
import java.util.*

@Serializable
class DeathCertificate(
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

    @Serializable(with = UUIDSerializer::class)
    @SerialName("person_id")
    val personId: UUID,

    val name: String,
    val sex: Sex,
    val color: Color,
    @SerialName("civil_status")
    val civilStatus: CivilStatus,
    val age: Int,
    @SerialName("municipality_of_birth")
    val placeOfBirth: Municipality,
    @SerialName("identity_document")
    val identityDocument: String,
    @SerialName("identity_document_type")
    val identityDocumentType: IdentityDocumentType,
    @SerialName("voter_identity")
    val voterIdentity: String?,
    val residence: Address,
    val affiliation: Affiliation,
    @Serializable(with = LocalDateTimeSerializer::class)
    @SerialName("date_time_of_death")
    val dateTimeOfDeath: LocalDateTime,
    @SerialName("place_of_death")
    val placeOfDeath: String,
    @SerialName("cause_of_death")
    val causeOfDeath:String,
    @SerialName("place_of_burial_or_cremation")
    val placeOfBurialOrCremation: Address?,
    @SerialName("medical_document_declaring_death")
    val medicalDocumentDeclaringDeath: String?
):CivilRegistryDocument() {
    override fun createHash(): String {
        return ""
    }
}