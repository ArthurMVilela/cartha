package document.civilRegistry.birth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import util.serializer.UUIDSerializer
import java.util.*

@Serializable
class Grandparent(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @Serializable(with = UUIDSerializer::class)
    @SerialName("birth_certificate_id")
    val birthCertificateId: UUID,
    val name: String,
    val type: GrandparentType
) {

}