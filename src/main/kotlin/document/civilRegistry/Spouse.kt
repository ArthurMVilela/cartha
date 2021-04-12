package document.civilRegistry

import kotlinx.serialization.Serializable
import util.serializer.LocalDateSerializer
import java.time.LocalDate

/**
 * Representa um cônjuge
 *
 * @property id             identificador único
 * @property singleName     nome de solteiro
 * @property marriedName    nome de casado
 * @property birthday       data de nascimento
 * @property nationality    nacionalidade
 * @property affiliations   filiações do cônjuge

 */
@Serializable
class Spouse (
    val id: String,
    val singleName: String,
    val marriedName: String,
    @Serializable(with = LocalDateSerializer::class)
    val birthday: LocalDate,
    val nationality: String,
    val affiliations: List<Affiliation>

) {
}