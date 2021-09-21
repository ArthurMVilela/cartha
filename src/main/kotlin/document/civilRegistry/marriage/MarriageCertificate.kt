package document.civilRegistry.marriage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import document.DocumentStatus
import document.civilRegistry.CivilRegistryDocument
import document.civilRegistry.Registering
import util.serializer.LocalDateSerializer
import util.serializer.UUIDSerializer
import java.time.LocalDate
import java.util.*

@Serializable
class MarriageCertificate(
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

    val spouses: List<Spouse>,
    @Serializable(with = LocalDateSerializer::class)
    @SerialName("date_of_registry")
    val dateOfRegistry: LocalDate,
    @SerialName("matrimonial_regime")
    val matrimonialRegime: MatrimonialRegime
):CivilRegistryDocument() {
    override fun createHash(): String {
        return ""
    }
}