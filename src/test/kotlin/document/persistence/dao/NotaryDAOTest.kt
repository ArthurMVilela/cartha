package document.persistence.dao

import document.*
import document.persistence.tables.NotaryTable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

internal class NotaryDAOTest {
    val p = Notary(
        null,
        "Comp Ltd.",
        "11222333444422",
        "123456"
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
                SchemaUtils.drop(NotaryTable)
                SchemaUtils.create(NotaryTable)
            }
        }

        @AfterAll
        @JvmStatic
        internal fun afterAll() {
            transaction {
                SchemaUtils.drop(NotaryTable)
            }
        }
    }

    @AfterEach
    internal fun afterEach() {
        transaction {
            NotaryDAO.removeWhere(Op.build{NotaryTable.id neq null})
        }
    }

    @Test
    @DisplayName("Insert")
    fun testInsert(){
        val inserted = NotaryDAO.insert(p).toType()

        assertEquals(p.id, inserted?.id)
    }

    @Test
    @DisplayName("Test select")
    fun testSelect() {
        val inserted = NotaryDAO.insert(p).toType()
        val selected = NotaryDAO.select(inserted!!.id!!)?.toType()

        assertEquals(inserted.id, selected?.id)
        assertEquals(inserted.name, selected?.name)
        assertEquals(inserted.cnpj, selected?.cnpj)
    }

    @Test
    @DisplayName("Test Update")
    fun testUpdate() {
        val inserted = NotaryDAO.insert(p).toType()!!
        val new = Notary(
            inserted.id,
            "Comp SA",
            inserted.cnpj,
            inserted.cns
        )

        NotaryDAO.update(new)

        val updated = NotaryDAO.select(new.id!!)?.toType()

        println(Json.encodeToString(updated))

        assertEquals(inserted.id, updated?.id)
        assertNotEquals(inserted.name, updated?.name)
        assertEquals(inserted.cnpj, updated?.cnpj)
    }

    @Test
    @DisplayName("Test Delete")
    fun testDelete() {
        val inserted = NotaryDAO.insert(p).toType()!!

        NotaryDAO.remove(inserted.id!!)

        val selected = NotaryDAO.select(inserted.id!!)

        assertEquals(null, selected)
    }

}