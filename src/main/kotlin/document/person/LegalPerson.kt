package document.person

import kotlinx.serialization.Serializable
import util.serializer.UUIDSerializer
import java.util.*

/**
 * Representa uma pessoa juridica (empresa)
 *
 * @property cnpj   cnpj da empresa
 */
@Serializable
class LegalPerson(
    @Serializable(with = UUIDSerializer::class)
    override val id: UUID = createId(),
    @Serializable(with = UUIDSerializer::class)
    override var accountId: UUID? = null,
    override val name: String,
    val cnpj: String
): Person() {
}