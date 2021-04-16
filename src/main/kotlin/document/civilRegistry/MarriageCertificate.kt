package document.civilRegistry

import document.DocumentStatus
import kotlinx.serialization.Serializable
import util.serializer.LocalDateSerializer
import java.time.LocalDate

/**
 * Representa uma certidão de casamento
 *
 * @property firstSpouse            Primeiro cõnjuge
 * @property secondSpouse           Segundo cõnjuge
 * @property dateOfRegistry         Data do registro
 * @property matrimonialRegime      Regime de bens
 */
@Serializable
class MarriageCertificate(
    override val id: String?,
    override val status: DocumentStatus,
    override val officialId: String,
    override val notaryId: String,
    override val registration: String,
    override val registering:List<Registering>,

    val firstSpouse: Spouse,
    val secondSpouse: Spouse,
    @Serializable(with = LocalDateSerializer::class)
    val dateOfRegistry: LocalDate,
    val matrimonialRegime:MatrimonialRegime,
):CivilRegistryDocument() {
}