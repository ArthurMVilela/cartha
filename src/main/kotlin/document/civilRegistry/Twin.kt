package document.civilRegistry

import kotlinx.serialization.Serializable

/**
 * Presenta o regitro de gemeos numa certidão de nascimento
 *
 * @property id                     Identificador único
 * @property birthCertificateId     ID da certidão de nascimento que se relaciona a essa classe
 * @property registration           Matrícula da certidão de nascimento do gemeo
 * @property name                   Nome do gemeo
 */
@Serializable
class Twin(
    val id:String?,
    val birthCertificateId:String,
    val registration:String,
    val name:String
) {
    constructor(
        id:String?,
        birthCertificate: BirthCertificate
    ):this(id, birthCertificate.id!!, birthCertificate.registration, birthCertificate.name)
}