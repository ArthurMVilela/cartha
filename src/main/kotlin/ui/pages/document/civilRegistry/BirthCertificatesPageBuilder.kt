package ui.pages.document.civilRegistry

import document.Notary
import document.civilRegistry.birth.BirthCertificate
import persistence.ResultSet
import ui.pages.Page
import ui.pages.PageBuilder

class BirthCertificatesPageBuilder: PageBuilder() {
    override val page: Page = Page("documentBirthCertificates.ftl", mutableMapOf())

    fun setResultSet(resultSet: ResultSet<BirthCertificate>?) {
        page.data["birthCertificates"] = resultSet?.rows
        page.data["currentPage"] = resultSet?.currentPage?:0
        page.data["numberOfPages"] = resultSet?.numberOfPages?:0
    }
}