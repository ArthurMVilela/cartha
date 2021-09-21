package document.handlers.address

import document.address.UF
import kotlinx.serialization.Serializable

@Serializable
class CreateMunicipalityRequest(
    val name: String,
    val uf: UF
)