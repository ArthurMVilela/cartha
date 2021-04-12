package document.civilRegistry

import document.DocumentStatus
import kotlinx.serialization.Serializable
import util.serializer.LocalDateSerializer
import java.time.LocalDate

/**
 * Representa uma certidão de casamento
 *
 * @property registration           Matrícula
 * @property firstSpouse            Primeiro cõnjuge
 * @property secondSpouse           Segundo cõnjuge
 * @property dateOfRegistry         Data do registro
 * @property matrimonialRegime      Regime de bens
 * @property observations           Observações
 */
@Serializable
class MarriageCertificate(
    override val id: String?,
    override val status: DocumentStatus,
    override val officialId: String,
    override val notaryId: String,

    val registration: String,
    val firstSpouse: Spouse,
    val secondSpouse: Spouse,
    @Serializable(with = LocalDateSerializer::class)
    val dateOfRegistry: LocalDate,
    val matrimonialRegime:MatrimonialRegime,
    val observations:String
):CivilRegistryDocument() {
}