package document.person

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import util.serializer.UUIDSerializer
import java.util.*

/**
 * Representa uma pessoa física que se relaciona a documentos
 *
 * @property id             identificador único
 * @property accountId      identificador único da conta associada à essa pessoa
 * @property name           nome do indivíduo
 */
@Serializable
abstract class Person (
) {
    @Serializable(with = UUIDSerializer::class)
    abstract val id: UUID
    @Serializable(with = UUIDSerializer::class)
    @SerialName("account_id")
    abstract var accountId: UUID?
    abstract val name: String

    companion object {
        fun createId():UUID {
            return UUID.randomUUID()
        }
    }
}