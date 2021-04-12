package document

import kotlinx.serialization.Serializable

/**
 * Representa um oficial(tabelião/funcionário) que autentifica um documento
 */
@Serializable
data class Official(
    override val id: String?,
    override val name: String,
    val cpf: String,
    val sex: Sex
): Person() {
}