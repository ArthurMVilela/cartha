package document.handlers.person

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import document.person.Sex
import util.serializer.UUIDSerializer
import java.util.*

@Serializable
class CreateOfficialRequest(
    @Serializable(with = UUIDSerializer::class)
    @SerialName("account_id")
    val accountId: UUID? = null,
    val name: String,
    val cpf: String,
    val sex: Sex,
    @Serializable(with = UUIDSerializer::class)
    @SerialName("notary_id")
    val notaryId:UUID
)