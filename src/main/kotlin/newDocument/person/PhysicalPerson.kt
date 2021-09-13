package newDocument.person

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import util.serializer.LocalDateSerializer
import util.serializer.UUIDSerializer
import java.time.LocalDate
import java.util.*

/**
 * Representa uma pessoa física (indivíduo)
 *
 * @property cpf            cpf do indivíduo
 * @property birthday       data de nascimento do indivíduo
 * @property sex            sexo do indivíduo
 * @property color          cor/raça do indivíduo
 * @property civilStatus    estado cívil do indivíduo
 */
@Serializable
class PhysicalPerson(
    @Serializable(with = UUIDSerializer::class)
    override val id: UUID = createId(),
    @Serializable(with = UUIDSerializer::class)
    override var accountId: UUID? = null,
    override val name: String,
    val cpf: String,
    @Serializable(with = LocalDateSerializer::class)
    val birthday: LocalDate,
    val sex: Sex,
    val color: Color,
    @SerialName("civil_status")
    val civilStatus: CivilStatus,
    val nationality: String
): Person() {
}