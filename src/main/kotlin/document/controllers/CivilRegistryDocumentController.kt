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
        builder.append(createCheckNumber(builder.toString()))

        return builder.toString()
    }

    private fun createCheckNumber(registrationNumber: String):String {
        var dv1sum = 0
        for (i in registrationNumber.indices) {
            val m = when {
                i+1 <= 10 -> i+1
                i+1%10 == 0 -> 10
                else -> {
                    i + 1 -(10 * ((i + 1) / 10))
                }
            }

            dv1sum += registrationNumber[i].toInt() * m
        }

        var dv2sum = 0
        for (i in registrationNumber.indices) {
            val m = when {
                i+2 <= 10 -> i+2
                i+2%10 == 0 -> 10
                else -> {
                    i + 2 -(10 * ((i + 2) / 10))
                }
            }

            dv2sum += registrationNumber[i].toInt() * m
        }

        val dv1 = when(dv1sum%11) {
            10 -> 1
            else -> dv1sum%11
        }

        val dv2 = when(dv2sum%11) {
            10 -> 1
            else -> dv2sum%11
        }

        return "$dv2$dv1"
    }
}