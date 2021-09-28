package document.handlers.civilRegistry.birth

import document.civilRegistry.birth.GrandparentType
import document.handlers.address.CreateMunicipalityRequest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import util.serializer.UUIDSerializer
import java.util.*

@Serializable
class CreateGrandparentRequest(
    @Serializable(with = UUIDSerializer::class)
    @SerialName("person_id")
    val personId: UUID?,
    val cpf: String,
    val name: String,
    val type: GrandparentType,
    val municipality: CreateMunicipalityRequest
)