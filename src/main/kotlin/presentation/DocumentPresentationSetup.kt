package presentation

import document.Notary
import document.controllers.NotaryController
import java.util.*

class DocumentPresentationSetup {
    fun setupNotary() {
        val controller = NotaryController()

        controller.createNotary(Notary(
            UUID.fromString(System.getenv("PRESENTATION_TEST_NOTARY_ID")!!),
            System.getenv("PRESENTATION_TEST_NOTARY_NAME")!!,
            System.getenv("PRESENTATION_TEST_NOTARY_CNPJ")!!,
            System.getenv("PRESENTATION_TEST_NOTARY_CNS")!!
        ))
    }
}