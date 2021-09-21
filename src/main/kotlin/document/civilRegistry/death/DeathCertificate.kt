package document.civilRegistry.death

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import document.DocumentStatus
import document.address.Address
import document.address.Municipality
import document.civilRegistry.Affiliation
import document.civilRegistry.CivilRegistryDocument
import document.civilRegistry.IdentityDocumentType
import document.civilRegistry.Registering
import document.person.CivilStatus
import document.person.Color
import document.person.Sex
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