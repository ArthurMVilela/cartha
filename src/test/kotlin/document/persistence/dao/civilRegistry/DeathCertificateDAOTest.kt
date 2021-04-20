package document.persistence.dao.civilRegistry

import document.*
import document.civilRegistry.Affiliation
import document.civilRegistry.DeathCertificate
import document.civilRegistry.Registering
import document.persistence.dao.*
import document.persistence.tables.*
import document.persistence.tables.civilRegistry.AffiliationTable
import document.persistence.tables.civilRegistry.CivilRegistryDocumentTable
import document.persistence.tables.civilRegistry.DeathCertificateTable
import document.persistence.tables.civilRegistry.RegisteringTable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month

internal class DeathCertificateDAOTest {
    val notary = Notary(null,"Cartório do Dr.Cicarno", "11222333444455", "111111")
    val official = Official(null, "Dr. Cicrano", "11122233344", Sex.Male)
    val affiliationPerson = PhysicalPerson(
        null,
        "Cicrana",
        "11122233344",
        LocalDate.of(2000, Month.JANUARY, 1),
        Sex.Male,
        Color.White,
        CivilStatus.Single,
        "brasileiro"
    )
    var affiliation:Affiliation
    val person = PhysicalPerson(
        null,
        "Fulano",
        "11122233344",
        LocalDate.of(2000, Month.JANUARY, 1),
        Sex.Male,
        Color.White,
        CivilStatus.Single,
        "brasileiro"
    )
    var p: DeathCertificate

    init {
        notary.id = notary.createId()
        official.id = official.createId()
        affiliationPerson.id = affiliationPerson.createId()
        person.id = person.createId()

        affiliation = Affiliation(null, null, affiliationPerson, UF.SP, "Mauá")
        affiliation.id = affiliation.createId()

        p = DeathCertificate(
            null,
            DocumentStatus.Valid,
            official.id!!,
            notary.id!!,

            "11111111112222",
            listOf(),

            person,

            "Ribeirão Pires SP",
            "11.222.555-x",
            affiliation,
            "Rua sei lá 188, Ribeirão Pires SP",

            LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0),
            "Hospital Santa Ana",
            "Asfixia",
            null,
            "declaração de óbito 1111"
        )

        p.id = p.createId()
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
                    PersonTable, PhysicalPersonTable, OfficialTable, NotaryTable, AffiliationTable,
                    DocumentTable, RegisteringTable, CivilRegistryDocumentTable, DeathCertificateTable
                )
                SchemaUtils.create(
                    PersonTable, PhysicalPersonTable, OfficialTable, NotaryTable, AffiliationTable,
                    DocumentTable, RegisteringTable, CivilRegistryDocumentTable, DeathCertificateTable
                )
            }
        }

        @AfterAll
        @JvmStatic
        internal fun afterAll() {
            transaction {
                SchemaUtils.drop(
                    PersonTable, PhysicalPersonTable, OfficialTable, NotaryTable, AffiliationTable,
                    DocumentTable, RegisteringTable, CivilRegistryDocumentTable, DeathCertificateTable
                )
            }
        }
    }

    @BeforeEach
    internal fun setUp() {
        NotaryDAO.insert(notary)
        OfficialDAO.insert(official)
        PhysicalPersonDAO.insert(affiliationPerson)
        PhysicalPersonDAO.insert(person)
    }

    @AfterEach
    internal fun afterEach() {
        transaction {


            DeathCertificateDAO.removeWhere(Op.build { DeathCertificateTable.id neq null })
            CivilRegistryDocumentDAO.removeWhere(Op.build { CivilRegistryDocumentTable.id neq null })
            DocumentDAO.removeWhere(Op.build { DocumentTable.id neq null })

            AffiliationDAO.removeWhere(Op.build { AffiliationTable.id neq null })

            PhysicalPersonDAO.removeWhere(Op.build { PhysicalPersonTable.id neq null })
            OfficialDAO.removeWhere(Op.build { OfficialTable.id neq null })
            PersonDAO.removeWhere(Op.build { PersonTable.id neq null })
        }
    }

    @Test
    @DisplayName("Insert")
    fun testInsert(){
        val inserted = DeathCertificateDAO.insert(p)!!.toType()

        Assertions.assertEquals(p.id, inserted?.id)
    }

    @Test
    @DisplayName("Test select")
    fun testSelect() {
        val inserted = DeathCertificateDAO.insert(p).toType()
        val selected = DeathCertificateDAO.select(inserted!!.id!!)?.toType()

        Assertions.assertEquals(p.id, selected?.id)
        Assertions.assertEquals(p.status, selected?.status)
        Assertions.assertEquals(p.notaryId, selected?.notaryId)
        Assertions.assertEquals(p.officialId, selected?.officialId)
        Assertions.assertEquals(p.registration, selected?.registration)
        Assertions.assertEquals(p.affiliation.id, selected?.affiliation!!.id)
        Assertions.assertEquals(p.affiliation.name, selected?.affiliation!!.name)

        selected?.registering!!.forEach {
            var eq = false
            for (i in p.registering) {
                if (Json.encodeToString(i).equals(Json.encodeToString(it))) {
                    eq = true
                    println(i)
                    break
                }
            }
            assert(eq)
        }
    }

    @Test
    @DisplayName("Test Update")
    fun testUpdate() {
        val inserted = DeathCertificateDAO.insert(p).toType()!!
        val new = DeathCertificate(
            inserted.id, DocumentStatus.Invalid, inserted.officialId, inserted.notaryId,
            "1111111111111111", listOf(
                Registering(null, inserted.id!!, "Anotação 1 mudada"),
                Registering(null, inserted.id!!, "Anotação 2"),
                Registering(null, inserted.id!!, "Anotação 3")
            ),
            person,

            "Ribeirão Pires SP",
            "11.222.555-x",
            inserted.affiliation,
            "Rua sei lá 188, Ribeirão Pires SP",

            LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0),
            "Hospital Santa Ana",
            "Asfixia",
            null,
            "declaração de óbito 1111"
        )

        new.registering.get(0).id = new.registering.get(0).createId()
        new.registering.get(1).id = new.registering.get(1).createId()
        new.registering.get(2).id = new.registering.get(2).createId()

        DeathCertificateDAO.update(new)

        val updated = DeathCertificateDAO.select(new.id!!)?.toType()

        Assertions.assertEquals(inserted.id, updated?.id)
        Assertions.assertEquals(inserted.officialId, updated?.officialId)
        Assertions.assertEquals(inserted.notaryId, updated?.notaryId)
        Assertions.assertEquals(inserted.registering.size, updated?.registering!!.size)
        Assertions.assertNotEquals(inserted.status, updated?.status)

        updated?.registering!!.forEach {
            var eq = false
            for (i in p.registering) {
                if (Json.encodeToString(i).equals(Json.encodeToString(it))) {
                    eq = true
                    println(i)
                    break
                }
            }
            assert(eq)
        }
    }

    @Test
    @DisplayName("Test Delete")
    fun testDelete() {
        val inserted = DeathCertificateDAO.insert(p).toType()!!

        DeathCertificateDAO.remove(inserted.id!!)

        val selected = DeathCertificateDAO.select(inserted.id!!)
        val registering = RegisteringDAO.selectMany(Op.build{RegisteringTable.documentID eq inserted.id})
        var count = 0
        transaction {
            count = registering.toList().size
        }

        Assertions.assertEquals(null, selected)
        Assertions.assertEquals(0, count)
    }
}