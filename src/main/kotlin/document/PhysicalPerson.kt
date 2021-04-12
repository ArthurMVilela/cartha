package document

import kotlinx.serialization.Serializable
import util.serializer.LocalDateSerializer
import java.time.LocalDate

/**
 * Representa uma pessoa física (indivíduo)
 *
 * @property cpf            cpf do indivíduo
 * @property birthDay       data de nascimento do indivíduo
 * @property sex            sexo do indivíduo
 * @property color          cor/raça do indivíduo
 * @property civilStatus    estado cívil do indivíduo
 */
@Serializable
class PhysicalPerson(
    override val id: String?,
    override val name: String,
    val cpf: String,
    @Serializable(with = LocalDateSerializer::class)
    val birthDay: LocalDate,
    val sex: Sex,
    val color: Color,
    val civilStatus: CivilStatus
):Person() {
}