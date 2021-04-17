package document.persistence.dao.civilRegistry

import document.*
import document.civilRegistry.Affiliation
import document.civilRegistry.Spouse
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

internal class SpouseDAOTest {
    val affiliationPerson = PhysicalPerson(
        null,
        "Cicrano pai",
        "11122233344",
        LocalDate.of(1960, Month.JANUARY, 1),
        Sex.Male,
        Color.White,
        CivilStatus.Widowed,
        "brasileiro"
    )
    val person = PhysicalPerson(
        null,
        "Cicrano Jr",
        "11122233344",
        LocalDate.of(2000, Month.JANUARY, 1),
        Sex.Male,
        Color.White,
        CivilStatus.Single,
        "brasileiro"
    )
    var p:Spouse

    init {
        affiliationPerson.id = affiliationPerson.createId()
        println(affiliationPerson.id)
//        PhysicalPersonDAO.insert(affiliationPerson)
        person.id = person.createId()
        println(person.id)
//        PhysicalPersonDAO.insert(person)

        p = Spouse(
            null, "Cicrano Jr", "Cicrano Jr Pereira", listOf(
                Affiliation(null, affiliationPerson, null, null)
            ), person
        )

        p.id = p.createId()
        p.affiliations.get(0).id = p.affiliations.get(0).createId()
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
//                SchemaUtils.drop(
//                    SpouseAffiliationTable,
//                    SpouseTable,
//                    AffiliationTable,
//                    PhysicalPersonTable,  PersonTable,
//                )
                SchemaUtils.create(
                    PersonTable, PhysicalPersonTable,
                    AffiliationTable,
                    SpouseTable,
                    SpouseAffiliationTable
                )
            }
        }

        @AfterAll
        @JvmStatic
        internal fun afterAll() {
            transaction {
                SchemaUtils.drop(
                    SpouseAffiliationTable,
                    SpouseTable,
                    AffiliationTable,
                    PhysicalPersonTable, PersonTable,
                )
            }
        }
    }

    @BeforeEach
    internal fun setUp() {
        PhysicalPersonDAO.insert(affiliationPerson)
        PhysicalPersonDAO.insert(person)
    }

    @AfterEach
    internal fun afterEach() {
        transaction {
            SpouseDAO.removeWhere(Op.build { SpouseTable.id neq null })
            AffiliationDAO.removeWhere(Op.build { AffiliationTable.id neq null })
            PhysicalPersonDAO.removeWhere(Op.build { PhysicalPersonTable.id neq null })
        }
    }

    @Test
    @DisplayName("Insert")
    fun testInsert(){
        val inserted = SpouseDAO.insert(p).toType()

        assertEquals(p.id, inserted?.id)
        assertEquals(p.singleName, inserted?.singleName)
        assertEquals(p.marriedName, inserted?.marriedName)
    }

    @Test
    @DisplayName("Test select")
    fun testSelect() {
        val inserted = SpouseDAO.insert(p).toType()
        val selected = SpouseDAO.select(inserted!!.id!!)?.toType()

        assertEquals(p.id, selected?.id)
        assertEquals(p.singleName, selected?.singleName)
        assertEquals(p.marriedName, selected?.marriedName)
    }

    @Test
    @DisplayName("Test Update")
    fun testUpdate() {
        val inserted = SpouseDAO.insert(p).toType()!!
        val new = Spouse(
            inserted.id!!,
            inserted.singleName,
            "Nome novo",
            inserted.affiliations,
            inserted.personId,
            inserted.birthday,
            inserted.nationality
        )

        SpouseDAO.update(new)

        val updated = SpouseDAO.select(new.id!!)

        assertEquals(inserted.id, updated?.id!!.value)
        assertEquals(inserted.singleName, updated?.singleName)
        assertNotEquals(inserted.marriedName, updated?.marriedName)
    }

    @Test
    @DisplayName("Test Delete")
    fun testDelete() {
        val inserted = SpouseDAO.insert(p).toType()!!

        SpouseDAO.remove(inserted.id!!)

        val selected = SpouseDAO.select(inserted.id!!)

        assertEquals(null, selected)
    }
}