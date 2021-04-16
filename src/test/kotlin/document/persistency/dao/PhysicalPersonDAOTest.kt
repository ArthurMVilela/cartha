package document.persistency.dao

import document.*
import document.persistency.tables.PersonTable
import document.persistency.tables.PhysicalPersonTable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate
import java.time.Month

internal class PhysicalPersonDAOTest {
    val p = PhysicalPerson(
        null,
        "Fulano",
        "11122233344",
        LocalDate.of(2000, Month.JANUARY, 1),
        Sex.Male,
        Color.White,
        CivilStatus.Single,
        "brasileiro"
    )

    init {
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
                SchemaUtils.drop(PersonTable, PhysicalPersonTable)
                SchemaUtils.create(PersonTable, PhysicalPersonTable)
            }
        }

        @AfterAll
        @JvmStatic
        internal fun afterAll() {
            transaction {
                SchemaUtils.drop(PersonTable, PhysicalPersonTable)
            }
        }
    }

    @AfterEach
    internal fun afterEach() {
        transaction {
            PhysicalPersonDAO.removeWhere(Op.build { PhysicalPersonTable.id neq null })
            PersonDAO.removeWhere(Op.build { PersonTable.id neq null })
        }
    }

    @Test
    @DisplayName("Insert")
    fun testInsert(){
        val inserted = PhysicalPersonDAO.insert(p).toType()

        assertEquals(p.id, inserted?.id)
    }

    @Test
    @DisplayName("Test select")
    fun testSelect() {
        val inserted = PhysicalPersonDAO.insert(p).toType()
        val selected = PhysicalPersonDAO.select(inserted!!.id!!)?.toType()

        assertEquals(inserted.id, selected?.id)
        assertEquals(inserted.name, selected?.name)
    }

    @Test
    @DisplayName("Test Update")
    fun testUpdate() {
        val inserted = PhysicalPersonDAO.insert(p).toType()!!
        val new = PhysicalPerson(
            inserted.id,
            "Cicrano da silva pereira",
            "22233344455",
            inserted.birthday,
            inserted.sex,
            inserted.color,
            inserted.civilStatus,
            inserted.nationality
        )

        PhysicalPersonDAO.update(new)

        val updated = PhysicalPersonDAO.select(new.id!!)?.toType()

        println(Json.encodeToString(updated))

        assertEquals(inserted.id, updated?.id)
        assertNotEquals(inserted.name, updated?.name)
        assertNotEquals(inserted.cpf, updated?.cpf)
    }

    @Test
    @DisplayName("Test Delete")
    fun testDelete() {
        val inserted = PhysicalPersonDAO.insert(p).toType()!!

        PhysicalPersonDAO.remove(inserted.id!!)

        val selected = PhysicalPersonDAO.select(inserted.id!!)

        assertEquals(null, selected)
    }

}