package document

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import util.serializer.LocalDateSerializer
import java.time.LocalDate

/**
 * Representa uma pessoa física (indivíduo)
 *
 * @property cpf        cpf do indivíduo
 * @property birthDay   data de nascimento do indivíduo
 * @property sex        sexo do indivíduo
 */
@Serializable
class PhysicalPerson(
    override val id: String?,
    override val name: String,
    val cpf: String,
    @Serializable(with = LocalDateSerializer::class)
    val birthDay: LocalDate,
    val sex: Sex
):Person() {
}