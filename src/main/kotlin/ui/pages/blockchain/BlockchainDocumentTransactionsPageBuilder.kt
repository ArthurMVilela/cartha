package ui.pages.blockchain

import blockchain.Transaction
import ui.pages.Page
import ui.pages.PageBuilder
import java.util.*

class BlockchainDocumentTransactionsPageBuilder: PageBuilder() {
    override val page: Page = Page("blockchainDocumentTransactions.ftl", mutableMapOf())

    fun setDocumentId(id: UUID) {
        page.data["documentId"] = id
    }

    fun setTransactions(transactions: List<Transaction>) {
        page.data["transactions"] = transactions
    }
}