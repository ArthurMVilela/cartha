package document.civilRegistry

import document.DocumentStatus
import document.Notary
import document.Official
import kotlinx.serialization.Serializable

/**
 * Representa uma certidão de óbito
 */
@Serializable
class DeathCertificate(
    override val id: String?,
    override val officialId: String,
    override val notaryId: String,
    override val status: DocumentStatus
):CivilRegistryDocument() {
}