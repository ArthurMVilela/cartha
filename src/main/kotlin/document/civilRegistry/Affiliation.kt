package document.civilRegistry

import document.PhysicalPerson
import kotlinx.serialization.Serializable

/**
 * Representa uma filiação
 *
 * @property id             Identificador único
 * @property personId       ID da pessoa relacionada a esta filiação
 * @property name           Nome da pessoa a que se refere essa filiação
 * @property UF             Unidade federativa que a pessoa é natural
 * @property Municipality   Município que a pessoa é natural
 */
@Serializable
class Affiliation(
    val id:String?,
    val personId: String,
    val name:String,
    val UF:String?,
    val Municipality:String?
) {
    constructor(
        id:String?,
        person:PhysicalPerson,
        UF:String?,
        Municipality:String?
    ):this(id, person.id!!, person.name, UF, Municipality)
}