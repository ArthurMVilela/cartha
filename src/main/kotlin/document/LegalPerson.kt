package document

import kotlinx.serialization.Serializable

/**
 * Representa uma pessoa juridica (empresa)
 *
 * @property cnpj   cnpj da empresa
 */
@Serializable
class LegalPerson(
    override val id: String?,
    override val name: String,
    val cnpj: String
):Person() {
}