package newDocument.civilRegistry.marriage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import newDocument.DocumentStatus
import newDocument.civilRegistry.CivilRegistryDocument
import newDocument.civilRegistry.Registering
import util.serializer.LocalDateSerializer
import util.serializer.LocalDateTimeSerializer
import util.serializer.UUIDSerializer
import java.time.LocalDate
import java.util.*

@Serializable
class MarriageCertificate(
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

    @Serializable(with = LocalDateSerializer::class)
    @SerialName("date_of_registry")
    val dateOfRegistry: LocalDate,

    val spouses: List<Spouse>
):CivilRegistryDocument() {
    override fun createHash(): String {
        return ""
    }
}