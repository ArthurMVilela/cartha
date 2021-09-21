package document.handlers.civilRegistry.birth

import document.civilRegistry.birth.GrandparentType
import kotlinx.serialization.Serializable

@Serializable
class CreateGrandparentRequest(
    val name: String,
    val type: GrandparentType
)