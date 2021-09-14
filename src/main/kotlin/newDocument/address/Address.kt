package newDocument.address

import kotlinx.serialization.Serializable
import util.serializer.UUIDSerializer
import java.util.*

@Serializable
class Address(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String?,
    val street: String,
    val number: String,
    val complement: String,
    val neighborhood: String,
    val municipality: Municipality
) {

}