package newDocument.civilRegistry

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import newDocument.Document
import newDocument.DocumentStatus
import util.serializer.UUIDSerializer
import java.time.LocalDate
import java.util.*

/**
 * Representa um documento de registro civil
 *
 * @property registrationNumber       Matrícula do documento
 * @property registering              Averbações
 */
@Serializable
abstract class CivilRegistryDocument(
):Document() {
    @SerialName("registration_number")
    abstract var registrationNumber:String?
    abstract val registering: MutableList<Registering>

    companion object {
        fun createRegistrationNumber(
            cns: String,
            storageCode: StorageCode,
            bookType: RegistryBookType,
            date: LocalDate,
            book: String
        ):String {
            return "$cns${storageCode.value} 55 ${date.year} ${bookType.value} $book 001 0000000 11"
        }
    }


}