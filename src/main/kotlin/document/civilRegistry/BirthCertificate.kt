package document.civilRegistry

import document.DocumentStatus
import document.Notary
import document.Official
import kotlinx.serialization.Serializable

/**
 * Representa uma certidão de casamento
 */
@Serializable
class BirthCertificate(
    override val id: String?,
    override val status: DocumentStatus,
    override val official: Official,
    override val notary: Notary
):CivilRegistryDocument() {

}