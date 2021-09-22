package document.handlers.civilRegistry.marriage

import document.civilRegistry.marriage.MatrimonialRegime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import util.serializer.LocalDateSerializer
import util.serializer.UUIDSerializer
import java.time.LocalDate
import java.util.*

@Serializable
class CreateMarriageCertificateRequest(
    @Serializable(with = UUIDSerializer::class)
    @SerialName("official_id")
    val officialId:UUID,
    @Serializable(with = UUIDSerializer::class)
    @SerialName("notary_id")
    val notaryId:UUID,
    val spouses:List<CreateSpouseRequest>,
    @Serializable(with = LocalDateSerializer::class)
    @SerialName("date_of_registry")
    val dateOfRegistry: LocalDate,
    @SerialName("matrimonial_regime")
    val matrimonialRegime: MatrimonialRegime
) {
}