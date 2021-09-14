package newDocument.controllers

import newDocument.Notary
import java.util.*

class NotaryController {
    fun createNotary(notary: Notary): Notary {
        return notary
    }
    fun getNotary(id: UUID): Notary? {
        return Notary(
            id,
            "Teste",
            "11222333444455",
            "1122233"
        )
    }
    fun getNotary(cnpj: String): Notary? {
        return Notary(
            Notary.createId(),
            "Teste",
            cnpj,
            "1122233"
        )
    }
}