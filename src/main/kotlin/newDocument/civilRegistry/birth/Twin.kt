package newDocument.civilRegistry.birth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import util.serializer.UUIDSerializer
import java.util.*

@Serializable
class Twin(
    @Serializable(with = UUIDSerializer::class)
    @SerialName("birth_certificate_id")
    val birthCertificateId: UUID,
    val name: String,
    @SerialName("registration_number")
    val registrationNumber: String
) {

}