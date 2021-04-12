package document

import kotlinx.serialization.Serializable

/**
 * Representa um cartório
 *
 * @property id     identificador único
 * @property name   nome (razão social) do cartório
 * @property cnpj   CNPJ do cartório
 */
@Serializable
class Notary (
    val id: String?,
    val name: String,
    val cnpj: String
)