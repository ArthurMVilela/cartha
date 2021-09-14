package newDocument.civilRegistry

import kotlinx.serialization.Serializable
import newDocument.address.Municipality
import util.serializer.UUIDSerializer
import java.util.*

@Serializable
class Affiliation(
    @Serializable(with = UUIDSerializer::class)
    val id:UUID,
    @Serializable(with = UUIDSerializer::class)
    val documentId:UUID,
    val name:String,
    val municipality: Municipality
)