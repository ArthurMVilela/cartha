package document

import kotlinx.serialization.Serializable
import util.serializer.UUIDSerializer
import java.util.*

@Serializable
class Notary(
    @Serializable(with = UUIDSerializer::class)
    val id:UUID,
    val name:String,
    val cnpj:String,
    val cns:String,
){
    companion object {
        fun createId():UUID {
            return UUID.randomUUID()
        }
    }
}