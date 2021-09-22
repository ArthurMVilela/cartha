package document.persistence

import document.persistence.tables.DocumentTable
import document.persistence.tables.NotaryTable
import document.persistence.tables.address.MunicipalityTable
import document.persistence.tables.civilRegistry.AffiliationTable
import document.persistence.tables.civilRegistry.CivilRegistryDocumentTable
import document.persistence.tables.civilRegistry.birth.BirthCertificateTable
import document.persistence.tables.civilRegistry.birth.GrandparentTable
import document.persistence.tables.civilRegistry.marriage.MarriageCertificateTable
import document.persistence.tables.civilRegistry.marriage.SpouseAffiliationTable
import document.persistence.tables.civilRegistry.marriage.SpouseTable
import document.persistence.tables.person.OfficialTable
import document.persistence.tables.person.PersonTable
import document.persistence.tables.person.PhysicalPersonTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseInitializer {
    companion object {
        fun loadConfigurations() {
            val host = System.getenv("DATABASE_HOST")
            val port = System.getenv("DATABASE_PORT")
            val database = System.getenv("DATABASE_NAME")
            val user = System.getenv("DATABASE_USER")
            val password = System.getenv("DATABASE_PASSWORD")
            val url = "jdbc:mysql://$host:$port/$database?verifyServerCertificate=false&useSSL=true"
            val db = Database.connect(
                url = url,
                driver = "com.mysql.jdbc.Driver",
                user = user,
                password = password,
            )

            TransactionManager.defaultDatabase = db
        }
        fun initialize() {
            transaction {
                SchemaUtils.create(
                    NotaryTable,
                    PersonTable, PhysicalPersonTable, OfficialTable,
                    MunicipalityTable,
                    DocumentTable, CivilRegistryDocumentTable,
                    AffiliationTable,
                    BirthCertificateTable, GrandparentTable,
                    MarriageCertificateTable, SpouseTable, SpouseAffiliationTable
                )
            }
        }
    }
}