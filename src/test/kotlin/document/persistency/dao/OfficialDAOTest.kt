package document.persistency.dao

import document.*
import document.persistency.tables.LegalPersonTable
import document.persistency.tables.OfficialTable
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

internal class OfficialDAOTest {
    val p = Official(
        null,
        "Dr Cicrano",
        "11122233344",
        Sex.Male
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
                SchemaUtils.drop(PersonTable, OfficialTable)
                SchemaUtils.create(PersonTable, OfficialTable)
            }
        }

        @AfterAll
        @JvmStatic
        internal fun afterAll() {
            transaction {
                SchemaUtils.drop(PersonTable, OfficialTable)
            }
        }
    }

    @AfterEach
    internal fun afterEach() {
        transaction {
            OfficialDAO.removeWhere(Op.build { OfficialTable.id neq null })
            PersonDAO.removeWhere(Op.build { PersonTable.id neq null })
        }
    }

    @Test
    @DisplayName("Insert")
    fun testInsert(){
        val inserted = OfficialDAO.insert(p).toType()

        assertEquals(p.id, inserted?.id)
    }

    @Test
    @DisplayName("Test select")
    fun testSelect() {
        val inserted = OfficialDAO.insert(p).toType()
        val selected = OfficialDAO.select(inserted!!.id!!)?.toType()

        assertEquals(inserted.id, selected?.id)
        assertEquals(inserted.name, selected?.name)
        assertEquals(inserted.cpf, selected?.cpf)
    }

    @Test
    @DisplayName("Test Update")
    fun testUpdate() {
        val inserted = OfficialDAO.insert(p).toType()!!
        val new = Official(
            inserted.id,
            "Dr Cicrana da silva",
            inserted.cpf,
            Sex.Female
        )

        OfficialDAO.update(new)

        val updated = OfficialDAO.select(new.id!!)?.toType()

        println(Json.encodeToString(updated))

        assertEquals(inserted.id, updated?.id)
        assertNotEquals(inserted.name, updated?.name)
        assertNotEquals(inserted.sex, updated?.sex)
        assertEquals(inserted.cpf, updated?.cpf)
    }

    @Test
    @DisplayName("Test Delete")
    fun testDelete() {
        val inserted = OfficialDAO.insert(p).toType()!!

        OfficialDAO.remove(inserted.id!!)

        val selected = OfficialDAO.select(inserted.id!!)

        assertEquals(null, selected)
    }

}