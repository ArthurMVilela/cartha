package newDocument.person

import kotlinx.serialization.Serializable
import util.serializer.UUIDSerializer
import java.util.*

/**
 * Representa um oficial(tabelião/funcionário) que autentifica um documento
 *
 * @property cpf        cpf do oficial
 * @property sex        sexo do oficial
 */
@Serializable
data class Official(
    @Serializable(with = UUIDSerializer::class)
    override val id: UUID = createId(),
    @Serializable(with = UUIDSerializer::class)
    override var accountId: UUID? = null,
    override val name: String,
    val cpf: String,
    val sex: Sex
): Person() {
}