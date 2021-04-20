package document

import kotlinx.serialization.Serializable

/**
 * Representa uma pessoa juridica (empresa)
 *
 * @property cnpj   cnpj da empresa
 */
@Serializable
class LegalPerson(
    override var id: String?,
    override val name: String,
    val cnpj: String
):Person() {
}