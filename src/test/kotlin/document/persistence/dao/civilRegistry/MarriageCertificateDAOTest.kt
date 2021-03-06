package document.persistence.dao.civilRegistry

import document.*
import document.civilRegistry.*
import document.persistence.dao.*
import document.persistence.tables.*
import document.persistence.tables.civilRegistry.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate
import java.time.Month

internal class MarriageCertificateDAOTest {
    val notary = Notary(null,"Cartório do Dr.Cicarno", "11222333444455", "111111")
    val official = Official(null, "Dr. Cicrano", "11122233344", Sex.Male)
    val firstPerson = PhysicalPerson(
        null,
        "Fulana da Silva",
        "11122233344",
        LocalDate.of(2000, Month.JANUARY, 1),
        Sex.Male,
        Color.White,
        CivilStatus.Single,
        "brasileiro"
    )
    val secondPerson = PhysicalPerson(
        null,
        "Cicrano Pereira",
        "11122233344",
        LocalDate.of(2000, Month.JANUARY, 1),
        Sex.Male,
        Color.White,
        CivilStatus.Single,
        "brasileiro"
    )

    var p: MarriageCertificate

    init {
        firstPerson.id = firstPerson.createId()
        secondPerson.id = secondPerson.createId()
        notary.id = notary.createId()
        official.id = official.createId()

        p = MarriageCertificate(
            null, DocumentStatus.Valid, official.id!!, notary.id!!,
            "11111111111", listOf(),
            Spouse(null, "Fulana da Silva", "Fulana da Silva Pereira", listOf(), firstPerson),
            Spouse(null, "Cicrano Pereira", "Cicrano da Silva Pereira", listOf(), secondPerson),
            LocalDate.of(2020, Month.JANUARY, 1),
            MatrimonialRegime.CommunityPropertyRuling
        )

        p.id = p.createId()

        p.registering.forEach {
            it.id = it.createId()
        }

        p.firstSpouse.id = p.firstSpouse.createId()
        p.secondSpouse.id = p.secondSpouse.createId()
    }

    companion object {
        @BeforeAll
        @JvmStatic
        internal fun beforaAll() {
            val db = Database.connect(
                url = "jdbc:mysql://localhost:3306/cartha_document_db",
                driver = "com.mysql.jdbc.Driver",
                user = "root",
                password = "test"
            )
            TransactionManager.defaultDatabase = db

            transaction {
                SchemaUtils.drop(
                    SpouseAffiliationTable,
                    PersonTable, PhysicalPersonTable, OfficialTable, NotaryTable, AffiliationTable, SpouseTable,
                    DocumentTable, RegisteringTable, CivilRegistryDocumentTable, MarriageCertificateTable
                )
                SchemaUtils.create(
                    PersonTable, PhysicalPersonTable, OfficialTable, NotaryTable, AffiliationTable, SpouseTable,
                    SpouseAffiliationTable,
                    DocumentTable, RegisteringTable, CivilRegistryDocumentTable, MarriageCertificateTable
                )
            }
        }

        @AfterAll
        @JvmStatic
        internal fun afterAll() {
            transaction {
                SchemaUtils.drop(
                    SpouseAffiliationTable,
                    PersonTable, PhysicalPersonTable, OfficialTable, NotaryTable, AffiliationTable, SpouseTable,
                    DocumentTable, RegisteringTable, CivilRegistryDocumentTable, MarriageCertificateTable
                )
            }
        }
    }

    @BeforeEach
    internal fun setUp() {
        NotaryDAO.insert(notary)
        OfficialDAO.insert(official)

        PhysicalPersonDAO.insert(firstPerson)
        PhysicalPersonDAO.insert(secondPerson)
    }

    @AfterEach
    internal fun afterEach() {
        transaction {
            MarriageCertificateDAO.removeWhere(Op.build { MarriageCertificateTable.id neq null })
            CivilRegistryDocumentDAO.removeWhere(Op.build { CivilRegistryDocumentTable.id neq null })
            DocumentDAO.removeWhere(Op.build { DocumentTable.id neq null })

            SpouseDAO.removeWhere(Op.build { SpouseTable.id neq null })
            AffiliationDAO.removeWhere(Op.build { AffiliationTable.id neq null })

            PhysicalPersonDAO.removeWhere(Op.build { PhysicalPersonTable.id neq null })
            OfficialDAO.removeWhere(Op.build { OfficialTable.id neq null })
            PersonDAO.removeWhere(Op.build { PersonTable.id neq null })
        }
    }

    @Test
    @DisplayName("Insert")
    fun testInsert(){
        val inserted = MarriageCertificateDAO.insert(p)!!.toType()

        assertEquals(p.id, inserted?.id)
    }

    @Test
    @DisplayName("Test select")
    fun testSelect() {
        val inserted = MarriageCertificateDAO.insert(p).toType()
        val selected = MarriageCertificateDAO.select(inserted!!.id!!)?.toType()

        assertEquals(p.id, selected?.id)
        assertEquals(p.status, selected?.status)
        assertEquals(p.notaryId, selected?.notaryId)
        assertEquals(p.officialId, selected?.officialId)
        assertEquals(p.registration, selected?.registration)
    }

    @Test
    @DisplayName("Test Update")
    fun testUpdate() {
        val inserted = MarriageCertificateDAO.insert(p).toType()!!
        val new = MarriageCertificate(
            inserted.id, DocumentStatus.Invalid, inserted.officialId, inserted.notaryId,
            inserted.registration, inserted.registering,
            inserted.firstSpouse, inserted.secondSpouse,
            LocalDate.of(2019, Month.JANUARY, 1),
            MatrimonialRegime.FinalPartitionOfAcquisitions
        )

        MarriageCertificateDAO.update(new)

        val updated = MarriageCertificateDAO.select(new.id!!)?.toType()

        assertEquals(inserted.id, updated?.id)
        assertEquals(inserted.officialId, updated?.officialId)
        assertEquals(inserted.notaryId, updated?.notaryId)
        assertEquals(inserted.registering.size, updated?.registering!!.size)
        assertNotEquals(inserted.dateOfRegistry, updated?.dateOfRegistry)
        assertNotEquals(inserted.matrimonialRegime, updated?.matrimonialRegime)
    }

    @Test
    @DisplayName("Test Delete")
    fun testDelete() {
        val inserted = MarriageCertificateDAO.insert(p).toType()!!

        MarriageCertificateDAO.remove(inserted.id!!)

        val selected = MarriageCertificateDAO.select(inserted.id!!)
        val registering = RegisteringDAO.selectMany(Op.build{RegisteringTable.documentID eq inserted.id})
        var count = 0
        transaction {
            count = registering.toList().size
        }

        Assertions.assertEquals(null, selected)
        Assertions.assertEquals(0, count)
    }
}