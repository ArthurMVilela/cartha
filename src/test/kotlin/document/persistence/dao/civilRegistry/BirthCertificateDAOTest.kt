package document.persistence.dao.civilRegistry

import document.*
import document.civilRegistry.Affiliation
import document.civilRegistry.BirthCertificate
import document.civilRegistry.Grandparent
import document.civilRegistry.GrandparentType
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
import java.time.LocalTime
import java.time.Month

internal class BirthCertificateDAOTest {
    val notary = Notary(null,"Cartório do Dr.Cicarno", "11222333444455", "111111")
    val official = Official(null, "Dr. Cicrano", "11122233344", Sex.Male)
    val person = PhysicalPerson(
        null,
        "Cicrana",
        "11122233344",
        LocalDate.of(2000, Month.JANUARY, 1),
        Sex.Female,
        Color.White,
        CivilStatus.Single,
        "brasileiro"
    )
    val father = PhysicalPerson(
        null,
        "Cicrano pai",
        "11122233344",
        LocalDate.of(1980, Month.JANUARY, 1),
        Sex.Male,
        Color.White,
        CivilStatus.Single,
        "brasileiro"
    )
    var p:BirthCertificate

    init {
        notary.id = notary.createId()
        official.id = official.createId()
        person.id = person.createId()
        father.id = father.createId()

        p = BirthCertificate(
            null, DocumentStatus.Valid, official.id!!, notary.id!!,
            "11111111112222", listOf(),
            LocalTime.of(10,0), "Ribeirão Pires", UF.SP,"Ribeirão Pires", UF.SP,
            listOf(Affiliation(null, null, father, UF.SP, "Ribeirão Pires")),
            listOf(Grandparent(null, null, father, GrandparentType.Paternal, UF.SP, "Ribeirão Pires")),
            false, listOf(),
            LocalDate.of(2000, Month.JANUARY,1),
            "1111",
            person
        )
        p.id = p.createId()
    }

    companion object {
        @BeforeAll
        @JvmStatic
        internal fun beforeAll() {
            val db = Database.connect(
                url = "jdbc:mysql://localhost:3306/cartha_document_db",
                driver = "com.mysql.jdbc.Driver",
                user = "root",
                password = "test"
            )
            TransactionManager.defaultDatabase = db

            transaction {
                SchemaUtils.drop(
                    AffiliationTable, GrandparentTable,
                    TwinTable, BirthCertificateTable,
                    RegisteringTable,
                    CivilRegistryDocumentTable, DocumentTable,
                    NotaryTable, OfficialTable,
                    PhysicalPersonTable, PersonTable
                )
                SchemaUtils.create(
                    PersonTable, PhysicalPersonTable,
                    NotaryTable, OfficialTable,
                    DocumentTable, CivilRegistryDocumentTable,
                    RegisteringTable,
                    AffiliationTable, GrandparentTable,
                    BirthCertificateTable, TwinTable
                )
            }
        }

        @AfterAll
        @JvmStatic
        internal fun afterAll() {
            transaction {
                SchemaUtils.drop(
                    TwinTable, BirthCertificateTable,
                    RegisteringTable,
                    AffiliationTable, GrandparentTable,
                    CivilRegistryDocumentTable, DocumentTable,
                    NotaryTable, OfficialTable,
                    PhysicalPersonTable, PersonTable
                )
            }
        }
    }

    @BeforeEach
    internal fun setUp() {
        NotaryDAO.insert(notary)
        OfficialDAO.insert(official)
        PhysicalPersonDAO.insert(person)
        PhysicalPersonDAO.insert(father)
    }

    @AfterEach
    internal fun afterEach() {
        transaction {
            BirthCertificateDAO.removeWhere(Op.build { BirthCertificateTable.id neq null })
            AffiliationDAO.removeWhere(Op.build { AffiliationTable.id neq null })
            GrandparentDAO.removeWhere(Op.build { GrandparentTable.id neq null })
            CivilRegistryDocumentDAO.removeWhere(Op.build { CivilRegistryDocumentTable.id neq null })
            DocumentDAO.removeWhere(Op.build { DocumentTable.id neq null })

            PhysicalPersonDAO.removeWhere(Op.build { PhysicalPersonTable.id neq null })
            OfficialDAO.removeWhere(Op.build { OfficialTable.id neq null })
            PersonDAO.removeWhere(Op.build { PersonTable.id neq null })
        }
    }

    @Test
    @DisplayName("Insert")
    fun testInsert() {
        val inserted = BirthCertificateDAO.insert(p)!!.toType()!!

        assertEquals(p.id, inserted?.id)
        assertEquals(p.municipalityOfBirth, inserted?.municipalityOfBirth)
    }

    @Test
    @DisplayName("Test select")
    fun testSelect() {
        val inserted = BirthCertificateDAO.insert(p).toType()
        val selected = BirthCertificateDAO.select(inserted!!.id!!)?.toType()

        assertEquals(p.id, selected?.id)
        assertEquals(p.status, selected?.status)
        assertEquals(p.notaryId, selected?.notaryId)
        assertEquals(p.officialId, selected?.officialId)
        assertEquals(p.registration, selected?.registration)
    }

    @Test
    @DisplayName("Test Update")
    fun testUpdate() {
        val inserted = BirthCertificateDAO.insert(p).toType()!!
        println(inserted.id)
        val new = BirthCertificate(
            inserted.id, inserted.status, inserted.officialId, inserted.notaryId,
            inserted.registration, inserted.registering,
            inserted.timeOfBirth, "Mauá", inserted.ufOfBirth, inserted.municipalityOfRegistry, inserted.ufOfRegistry,
            inserted.affiliations, inserted.grandParents,
            inserted.twin, inserted.twins,
            inserted.dateOfRegistry, inserted.DNNumber,
            inserted.personId, inserted.cpf, inserted.name, inserted.dateOfBirth, inserted.sex
        )

        BirthCertificateDAO.update(new)

        val updated = BirthCertificateDAO.select(new.id!!)?.toType()

        assertEquals(inserted.id, updated?.id)
        assertEquals(inserted.officialId, updated?.officialId)
        assertEquals(inserted.notaryId, updated?.notaryId)
        assertEquals(inserted.registering.size, updated?.registering!!.size)
        assertEquals(inserted.status, updated?.status)
        assertNotEquals(inserted.municipalityOfBirth, updated?.municipalityOfBirth)

    }

    @Test
    @DisplayName("Test Delete")
    fun testDelete() {
        val inserted = BirthCertificateDAO.insert(p).toType()!!

        BirthCertificateDAO.remove(inserted.id!!)

        val selected = BirthCertificateDAO.select(inserted.id!!)

        assertEquals(null, selected)
    }
}