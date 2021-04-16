package document.persistency.dao

import document.Person
import document.persistency.tables.PersonTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

internal class PersonDAOTest {
    val p = object : Person() {
        override var id: String? = createId()
        override val name: String = "Fulano"
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
                SchemaUtils.create(PersonTable)
            }
        }

        @AfterAll
        @JvmStatic
        internal fun afterAll() {
            transaction {
                SchemaUtils.drop(PersonTable)
            }
        }
    }

    @AfterEach
    internal fun afterEach() {
        transaction {
            PersonDAO.removeWhere(Op.build { PersonTable.id neq null })
        }
    }

    @Test
    @DisplayName("Insert")
    fun testInsert(){
        val inserted = PersonDAO.insert(p).toType()

        assertEquals(p.id, inserted?.id)
    }

    @Test
    @DisplayName("Test select")
    fun testSelect() {
        val inserted = PersonDAO.insert(p).toType()
        val selected = PersonDAO.select(inserted!!.id!!)?.toType()

        assertEquals(inserted.id, selected?.id)
        assertEquals(inserted.name, selected?.name)
    }

    @Test
    @DisplayName("Test Update")
    fun testUpdate() {
        val inserted = PersonDAO.insert(p).toType()!!
        val new = object : Person() {
            override var id: String? = inserted.id
            override val name: String = "Cicrano"
        }

        PersonDAO.update(new)

        val updated = PersonDAO.select(new.id!!)?.toType()

        assertEquals(inserted.id, updated?.id)
        assertNotEquals(inserted.name, updated?.name)
    }

    @Test
    @DisplayName("Test Delete")
    fun testDelete() {
        val inserted = PersonDAO.insert(p).toType()!!

        PersonDAO.remove(inserted.id!!)

        val selected = PersonDAO.select(inserted.id!!)

        assertEquals(null, selected)
    }

}