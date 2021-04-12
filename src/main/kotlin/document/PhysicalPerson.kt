package document

import kotlinx.serialization.Serializable

/**
 * Representa uma pessoa física (indivíduo)
 *
 * @property cpf        cpf do indivíduo
 * @property sex        sexo do indivíduo
 */
@Serializable
class PhysicalPerson(
    override val id: String?,
    override val name: String,
    val cpf: String,
    val sex: Sex
):Person() {
}