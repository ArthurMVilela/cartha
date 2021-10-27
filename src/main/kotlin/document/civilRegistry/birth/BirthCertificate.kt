package document.civilRegistry.birth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import document.DocumentStatus
import document.address.Municipality
import document.civilRegistry.*
import document.person.Sex
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import util.serializer.LocalDateSerializer
import util.serializer.LocalDateTimeSerializer
import util.serializer.UUIDSerializer
import java.security.MessageDigest
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
    val personId: UUID?,
    val cpf:String,
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
    @SerialName("dn_number")
    val dnNumber: String,
):CivilRegistryDocument() {
    init {
        if (hash == null) hash = createHash()
        if (registrationNumber == null) registrationNumber = createRegistrationNumber(
            "123456",
            StorageCode.IncorporatedStorage,
            RegistryBookType.A,
            dateOfRegistry,
            "00001"
        )
    }
    override fun createHash(): String {
        val md = MessageDigest.getInstance("SHA-256")
        var content = Base64.getUrlDecoder().decode(id.toString().toByteArray())
        content = content.plus(Base64.getUrlDecoder().decode(officialId.toString().toByteArray()))
        content = content.plus(Base64.getUrlDecoder().decode(notaryId.toString().toByteArray()))
        content = content.plus(registrationNumber?.toByteArray()?: ByteArray(0))
        content = content.plus(Json.encodeToString(registering).toByteArray())
        content = content.plus(Base64.getUrlDecoder().decode(personId.toString().toByteArray()))
        content = content.plus(cpf.toByteArray())
        content = content.plus(name.toByteArray())
        content = content.plus(sex.toString().toByteArray())
        content = content.plus(Json.encodeToString(municipalityOfBirth).toByteArray())
        content = content.plus(Json.encodeToString(municipalityOfRegistry).toByteArray())
        content = content.plus(placeOfBirth.toByteArray())
        content = content.plus(Json.encodeToString(affiliation).toByteArray())
        content = content.plus(Json.encodeToString(grandparents).toByteArray())
        content = content.plus(dateTimeOfBirth.toString().toByteArray())
        content = content.plus(dateOfRegistry.toString().toByteArray())
        content = content.plus(Json.encodeToString(twins).toByteArray())
        content = content.plus(dnNumber.toByteArray())

        return Base64.getUrlEncoder().encodeToString(md.digest(content))
    }
}