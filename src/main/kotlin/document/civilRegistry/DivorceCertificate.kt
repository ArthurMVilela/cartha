package document.civilRegistry

import document.DocumentStatus
import kotlinx.serialization.Serializable

/**
 * Representa uma certidão de divorcío
 */
@Serializable
class DivorceCertificate(
    override val id: String?,
    override val status: DocumentStatus,
    override val officialId: String,
    override val notaryId: String,
    override val registration: String,
    override val observationsAndRegistering: List<String>
):CivilRegistryDocument() {
}