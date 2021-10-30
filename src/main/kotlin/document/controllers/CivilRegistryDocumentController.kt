package document.controllers

import document.civilRegistry.RegistryBookType
import document.civilRegistry.StorageCode
import document.persistence.dao.NotaryDAO
import document.persistence.dao.civilRegistry.CivilRegistryDAO
import java.lang.Exception
import java.time.LocalDate
import java.util.*

class CivilRegistryDocumentController {
    private val notaryDAO = NotaryDAO()
    private val civilRegistryDAO = CivilRegistryDAO()

    fun createRegistrationNumber(
        notaryId: UUID,
        storageCode: StorageCode,
        bookType: RegistryBookType,
        date: LocalDate,
        book: Int,
        page: Int
    ): String {
        val notary = notaryDAO.select(notaryId)?:throw Exception("Cartório não encontrado.")
        val count = civilRegistryDAO.civilRegistryCount(notaryId)

        val builder = StringBuilder()

        builder.append(notary.cns)
        builder.append(storageCode.value)
        builder.append(55)
        builder.append(date.year)
        builder.append(bookType.value)
        builder.append("%05d".format(book))
        builder.append("%03d".format(page))
        builder.append("%07d".format(count + 1))
        builder.append("XX")

        return builder.toString()
    }
}