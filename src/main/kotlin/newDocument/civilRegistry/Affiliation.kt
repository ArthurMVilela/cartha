package newDocument.civilRegistry

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import newDocument.address.Municipality
import util.serializer.UUIDSerializer
import java.util.*

@Serializable
class Affiliation(
    @Serializable(with = UUIDSerializer::class)
    val id:UUID,
    @Serializable(with = UUIDSerializer::class)
    @SerialName("person_id")
    var personId:UUID?,
    @Serializable(with = UUIDSerializer::class)
    @SerialName("document_id")
    val documentId:UUID,
    val name:String,
    val municipality: Municipality
)