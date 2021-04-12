package document.civilRegistry

import document.CivilStatus
import document.Color
import document.DocumentStatus
import document.Sex
import kotlinx.serialization.Serializable
import util.serializer.LocalDateTimeSerializer
import java.time.LocalDateTime

/**
 * Representa uma certidão de óbito
 */
@Serializable
class DeathCertificate(
    override val id: String?,
    override val officialId: String,
    override val notaryId: String,
    override val status: DocumentStatus,
    override val registration:String,
    override val observationsAndRegistering:List<String>,

    val personId: String,

    val sex: Sex,
    val color: Color,
    val civilStatus: CivilStatus,
    val age: Int,
    val birthPlace: String,
    val documentOfIdentity: String,

    val affiliation: Affiliation,
    val residency:String,

    @Serializable(with = LocalDateTimeSerializer::class)
    val dateTimeOfDeath:LocalDateTime,
    val placeOfDeath:String,
    val causeOfDeath:String,
    val burialOrCremationLocation:String?,
    val documentDeclaringDeath: String,
):CivilRegistryDocument() {
}