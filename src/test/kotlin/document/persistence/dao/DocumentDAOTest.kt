package document.persistence.dao

import document.*
import document.persistence.tables.DocumentTable
import document.persistence.tables.NotaryTable
import document.persistence.tables.OfficialTable
import document.persistence.tables.PersonTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

internal class DocumentDAOTest {
    val notary = Notary(null,"Cartório do Dr.Cicarno", "11222333444455", "111111")
    val official = Official(null, "Dr. Cicrano", "11122233344", Sex.Male)

    var p:Document

    init {
        notary.id = notary.createId()
        official.id = official.createId()
        p = object : Document() {
            override var id: String? = createId()
            override val status: DocumentStatus = DocumentStatus.Valid
            override val officialId: String = official.id!!
            override val notaryId: String = notary.id!!
        }
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
                SchemaUtils.create(PersonTable, OfficialTable, NotaryTable, DocumentTable)
            }
        }

        @AfterAll
        @JvmStatic
        internal fun afterAll() {
            transaction {
                SchemaUtils.drop(PersonTable, OfficialTable, NotaryTable, DocumentTable)
            }
        }
    }

    @BeforeEach
    internal fun setUp() {
        NotaryDAO.insert(notary)
        OfficialDAO.insert(official)
    }

    @AfterEach
    internal fun afterEach() {
        transaction {
            DocumentDAO.removeWhere(Op.build { DocumentTable.id neq null })
            NotaryDAO.removeWhere(Op.build { NotaryTable.id neq null })
            OfficialDAO.removeWhere(Op.build { OfficialTable.id neq null })
        }
    }

    @Test
    @DisplayName("Insert")
    fun testInsert(){
        val inserted = DocumentDAO.insert(p).toType()

        assertEquals(p.id, inserted?.id)
        assertEquals(p.status, inserted?.status)
        assertEquals(p.notaryId, inserted?.notaryId)
        assertEquals(p.officialId, inserted?.officialId)
    }

    @Test
    @DisplayName("Test select")
    fun testSelect() {
        val inserted = DocumentDAO.insert(p).toType()
        val selected = DocumentDAO.select(inserted!!.id!!)?.toType()

        assertEquals(inserted.id, selected?.id)
        assertEquals(inserted.status, selected?.status)
        assertEquals(inserted.officialId, selected?.officialId)
        assertEquals(inserted.notaryId, selected?.notaryId)
    }

    @Test
    @DisplayName("Test Update")
    fun testUpdate() {
        val inserted = DocumentDAO.insert(p).toType()!!
        val new = object : Document() {
            override var id: String? = inserted.id
            override val status: DocumentStatus = DocumentStatus.Invalid
            override val officialId: String = inserted.officialId
            override val notaryId: String = inserted.notaryId
        }

        DocumentDAO.update(new)

        val updated = DocumentDAO.select(new.id!!)?.toType()

        assertEquals(inserted.id, updated?.id)
        assertEquals(inserted.officialId, updated?.officialId)
        assertEquals(inserted.notaryId, updated?.notaryId)
        assertNotEquals(inserted.status, updated?.status)
    }

    @Test
    @DisplayName("Test Delete")
    fun testDelete() {
        val inserted = DocumentDAO.insert(p).toType()!!

        DocumentDAO.remove(inserted.id!!)

        val selected = DocumentDAO.select(inserted.id!!)

        assertEquals(null, selected)
    }

}