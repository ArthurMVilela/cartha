package document.civilRegistry

import kotlinx.serialization.Serializable

/**
 * Representa uma filiação
 *
 * @property id             Identificador único
 * @property name           Nome da pessoa a que se refere essa filiação
 * @property UF             Unidade federativa que a pessoa é natural
 * @property Municipality   Município que a pessoa é natural
 */
@Serializable
class Affiliation(
    val id:String?,
    val name:String,
    val UF:String?,
    val Municipality:String?
) {
}