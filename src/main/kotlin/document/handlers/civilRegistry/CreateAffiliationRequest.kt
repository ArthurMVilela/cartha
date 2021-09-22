package document.handlers.civilRegistry

import document.address.Municipality
import document.handlers.address.CreateMunicipalityRequest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import util.serializer.UUIDSerializer
import java.util.*

@Serializable
class CreateAffiliationRequest(
    @Serializable(with = UUIDSerializer::class)
    @SerialName("person_id")
    var personId: UUID?,
    val name:String,
    val municipality: CreateMunicipalityRequest
) {
}