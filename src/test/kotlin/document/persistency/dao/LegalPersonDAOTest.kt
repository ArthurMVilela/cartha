package document.persistency.dao

import document.*
import document.persistency.tables.LegalPersonTable
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

internal class LegalPersonDAOTest {
    val p = LegalPerson(
        null,
        "Comp Ltd.",
        "1122233344444",
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
                SchemaUtils.drop(PersonTable, LegalPersonTable)
                SchemaUtils.create(PersonTable, LegalPersonTable)
            }
        }

        @AfterAll
        @JvmStatic
        internal fun afterAll() {
            transaction {
                SchemaUtils.drop(PersonTable, LegalPersonTable)
            }
        }
    }

    @AfterEach
    internal fun afterEach() {
        transaction {
            LegalPersonDAO.removeWhere(Op.build { LegalPersonTable.id neq null })
            PersonDAO.removeWhere(Op.build { PersonTable.id neq null })
        }
    }

    @Test
    @DisplayName("Insert")
    fun testInsert(){
        val inserted = LegalPersonDAO.insert(p).toType()

        assertEquals(p.id, inserted?.id)
    }

    @Test
    @DisplayName("Test select")
    fun testSelect() {
        val inserted = LegalPersonDAO.insert(p).toType()
        val selected = LegalPersonDAO.select(inserted!!.id!!)?.toType()

        assertEquals(inserted.id, selected?.id)
        assertEquals(inserted.name, selected?.name)
        assertEquals(inserted.cnpj, selected?.cnpj)
    }

    @Test
    @DisplayName("Test Update")
    fun testUpdate() {
        val inserted = LegalPersonDAO.insert(p).toType()!!
        val new = LegalPerson(
            inserted.id,
            "Comp SA",
            inserted.cnpj
        )

        LegalPersonDAO.update(new)

        val updated = LegalPersonDAO.select(new.id!!)?.toType()

        println(Json.encodeToString(updated))

        assertEquals(inserted.id, updated?.id)
        assertNotEquals(inserted.name, updated?.name)
        assertEquals(inserted.cnpj, updated?.cnpj)
    }

    @Test
    @DisplayName("Test Delete")
    fun testDelete() {
        val inserted = LegalPersonDAO.insert(p).toType()!!

        LegalPersonDAO.remove(inserted.id!!)

        val selected = LegalPersonDAO.select(inserted.id!!)

        assertEquals(null, selected)
    }

}