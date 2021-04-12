package document.civilRegistry

import kotlinx.serialization.Serializable

/**
 * Representa o registro de um avó/avô numa certidão de nascimento
 *
 * @property id             Identificador único
 * @property type           Tipo de avó/avo (Materno/Paterno)
 * @property name           Nome da pessoa a que se refere essa filiação
 * @property UF             Unidade federativa que a pessoa é natural
 * @property Municipality   Município que a pessoa é natural
 */
@Serializable
class Grandparent(
    val id:String?,
    val type:GrandparentType,
    val name:String,
    val UF:String?,
    val Municipality:String?
) {
}