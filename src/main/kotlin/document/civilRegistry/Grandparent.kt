package document.civilRegistry

import document.PhysicalPerson
import document.UF
import kotlinx.serialization.Serializable

/**
 * Representa o registro de um avó/avô numa certidão de nascimento.
 *
 * @property id             Identificador único
 * @property personId       ID da pessoa que se relaciona essa classe
 * @property type           Tipo de avó/avo (Materno/Paterno)
 * @property name           Nome da pessoa a que se refere essa filiação
 * @property UF             Unidade federativa que a pessoa é natural
 * @property municipality   Município que a pessoa é natural
 */
@Serializable
class Grandparent(
    val id:String?,
    val personId:String,
    val name:String,
    val type:GrandparentType,
    val UF:UF?,
    val municipality:String?
) {
    constructor(
        id:String?,
        person: PhysicalPerson,
        type:GrandparentType,
        UF:UF?,
        municipality:String?
    ):this(id, person.id!!, person.name, type, UF, municipality)
}