package document.civilRegistry

import document.PhysicalPerson
import kotlinx.serialization.Serializable
import util.serializer.LocalDateSerializer
import java.time.LocalDate

/**
 * Representa um cônjuge
 *
 * @property id             identificador único
 * @property personId       id da pessoa que é o conjuge
 * @property singleName     nome de solteiro
 * @property marriedName    nome de casado
 * @property birthday       data de nascimento
 * @property nationality    nacionalidade
 * @property affiliations   filiações do cônjuge
 */
@Serializable
class Spouse (
    val id: String?,
    val singleName: String,
    val marriedName: String,
    val affiliations: List<Affiliation>,
    val personId: String,
    @Serializable(with = LocalDateSerializer::class)
    val birthday: LocalDate,
    val nationality: String
) {
    constructor(
        id: String?,
        singleName: String,
        marriedName: String,
        affiliations: List<Affiliation>,
        person: PhysicalPerson
    ):this(id, singleName, marriedName, affiliations, person.id!!, person.birthday, person.nationality)
}