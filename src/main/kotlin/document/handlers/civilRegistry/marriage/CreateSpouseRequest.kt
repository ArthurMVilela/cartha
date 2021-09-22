package document.handlers.civilRegistry.marriage

import document.handlers.civilRegistry.CreateAffiliationRequest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import util.serializer.LocalDateSerializer
import util.serializer.UUIDSerializer
import java.time.LocalDate
import java.util.*

@Serializable
class CreateSpouseRequest(
    @Serializable(with = UUIDSerializer::class)
    @SerialName("person_id")
    val personId: UUID?,
    @SerialName("single_name")
    val singleName: String,
    @SerialName("married_name")
    val marriedName: String,
    @Serializable(with = LocalDateSerializer::class)
    val birthday: LocalDate,
    val nationality: String,
    val affiliation: List<CreateAffiliationRequest>
)