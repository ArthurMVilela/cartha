package ui.pages.blockchain

import blockchain.Transaction
import ui.pages.Page
import ui.pages.PageBuilder
import java.util.*

class BlockchainDocumentValidationPageBuilder: PageBuilder() {
    override val page: Page = Page("blockchainDocumentValidation.ftl", mutableMapOf())

    fun setValid(valid: Boolean) {
        page.data["valid"] = valid
    }

    fun setTransaction(transaction:Transaction) {
        page.data["transaction"] = transaction
    }
}