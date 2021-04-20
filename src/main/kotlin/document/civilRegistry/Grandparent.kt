package document.civilRegistry

import document.PhysicalPerson
import document.UF
import kotlinx.serialization.Serializable
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

/**
 * Representa o registro de um avó/avô numa certidão de nascimento.
 *
 * @property id             Identificador único
 * @property personId       ID da pessoa que se relaciona essa classe
 * @property type           Tipo de avó/avo (Materno/Paterno)
 * @property name           Nome da pessoa a que se refere essa filiação
 * @property uf             Unidade federativa que a pessoa é natural
 * @property municipality   Município que a pessoa é natural
 */
@Serializable
class Grandparent(
    var id:String?,
    var documentId:String?,
    val personId:String,
    val name:String,
    val type:GrandparentType,
    val uf:UF?,
    val municipality:String?
) {
    constructor(
        id:String?,
        documentId:String?,
        person: PhysicalPerson,
        type:GrandparentType,
        UF:UF?,
        municipality:String?
    ):this(id, documentId, person.id!!, person.name, type, UF, municipality)

    fun createId(): String {
        val md = MessageDigest.getInstance("SHA")
        val now = LocalDateTime.now(ZoneOffset.UTC)
        return Base64.getUrlEncoder().encodeToString(md.digest(now.toString().toByteArray()))
    }
}