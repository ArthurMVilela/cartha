package document.civilRegistry

import kotlinx.serialization.Serializable

/**
 * Presenta o regitro de gemeos numa sertidão de nascimento
 *
 * @property id             Identificador único
 * @property registration   Matrícula da certidão de nascimento do gemeo
 * @property name           Nome do gemeo
 */
@Serializable
class Twin(
    val id:String?,
    val registration:String,
    val name:String
) {
    constructor(id:String?, birthCertificate: BirthCertificate):this(id, birthCertificate.registration, birthCertificate.name)
}