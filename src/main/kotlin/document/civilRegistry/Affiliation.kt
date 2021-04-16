package document.civilRegistry

import document.PhysicalPerson
import document.UF
import kotlinx.serialization.Serializable

/**
 * Representa uma filiação
 *
 * @property id             Identificador único
 * @property personId       ID da pessoa relacionada a esta filiação
 * @property name           Nome da pessoa a que se refere essa filiação
 * @property uf             Unidade federativa que a pessoa é natural
 * @property municipality   Município que a pessoa é natural
 */
@Serializable
class Affiliation(
    val id:String?,
    val personId: String,
    val name:String,
    val uf:UF?,
    val municipality:String?
) {
    constructor(
        id:String?,
        person:PhysicalPerson,
        UF:UF?,
        Municipality:String?
    ):this(id, person.id!!, person.name, UF, Municipality)
}