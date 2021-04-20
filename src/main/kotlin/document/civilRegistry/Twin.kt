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
    var id:String?,
    val birthCertificateId:String,
    val twinBirthCertificateId:String,
    val registration:String,
    val name:String
) {
    constructor(
        id:String?,
        birthCertificate: BirthCertificate,
        twinBirthCertificate: BirthCertificate
    ):this(id, birthCertificate.id!!, twinBirthCertificate.id!!, birthCertificate.registration, birthCertificate.name)
}