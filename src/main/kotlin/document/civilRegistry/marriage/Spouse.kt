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
    @SerialName("single_name")
    val singleName: String,
    @SerialName("married_name")
    val marriedName: String,
    @Serializable(with = LocalDateSerializer::class)
    val birthday: LocalDate,
    @SerialName("local_of_birth")
    val localOfBirth: String,
    val nationality: String,
    val affiliation: List<Affiliation>
){
}