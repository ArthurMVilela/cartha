package document

import kotlinx.serialization.Serializable

/**
 * Representa um oficial(tabelião/funcionário) que autentifica um documento
 *
 * @property cpf        cpf do oficial
 * @property sex        sexo do oficial
 */
@Serializable
data class Official(
    override var id: String?,
    override val name: String,
    val cpf: String,
    val sex: Sex
): Person() {
}