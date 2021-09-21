package document.handlers.person

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import document.person.CivilStatus
import document.person.Color
import document.person.Sex
import util.serializer.LocalDateSerializer
import util.serializer.UUIDSerializer
import java.time.LocalDate
import java.util.*

@Serializable
class CreatePhysicalPersonRequest(
    @Serializable(with = UUIDSerializer::class)
    @SerialName("account_id")
    val accountId: UUID? = null,
    val name: String,
    val cpf: String,
    @Serializable(with = LocalDateSerializer::class)
    val birthday: LocalDate,
    val sex: Sex,
    val color: Color,
    @SerialName("civil_status")
    val civilStatus: CivilStatus,
    val nationality: String
)