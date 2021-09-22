package document.civilRegistry.marriage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import document.civilRegistry.Affiliation
import util.serializer.LocalDateSerializer
import util.serializer.UUIDSerializer
import java.time.LocalDate
import java.util.*

@Serializable
class Spouse(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @Serializable(with = UUIDSerializer::class)
    @SerialName("marriage_certificate_id")
    val marriageCertificateId: UUID,
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
    val affiliation: List<Affiliation>
){
}