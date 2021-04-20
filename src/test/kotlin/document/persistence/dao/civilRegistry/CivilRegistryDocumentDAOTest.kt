package document.persistence.dao.civilRegistry

import document.*
import document.civilRegistry.CivilRegistryDocument
import document.civilRegistry.Registering
import document.persistence.dao.DocumentDAO
import document.persistence.dao.NotaryDAO
import document.persistence.dao.OfficialDAO
import document.persistence.tables.DocumentTable
import document.persistence.tables.NotaryTable
import document.persistence.tables.OfficialTable
import document.persistence.tables.PersonTable
import document.persistence.tables.civilRegistry.CivilRegistryDocumentTable
import document.persistence.tables.civilRegistry.RegisteringTable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

internal class CivilRegistryDocumentDAOTest {
    val notary = Notary(null,"Cartório do Dr.Cicarno", "11222333444455", "111111")
    val official = Official(null, "Dr. Cicrano", "11122233344", Sex.Male)

    var p: CivilRegistryDocument

    init {
        notary.id = notary.createId()
        official.id = official.createId()

        p = object : CivilRegistryDocument() {
            override var id: String? = createId()
            override val status: DocumentStatus = DocumentStatus.Valid
            override val officialId: String = official.id!!
            override val notaryId: String = notary.id!!
            override val registration: String = "1111111111111111"
            override val registering: List<Registering> = listOf(
                Registering(null, id!!, "Anotação 1"),
                Registering(null, id!!, "Anotação 2")
            )
        }

        p.registering.forEach {
            it.id = it.createId()
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
                SchemaUtils.create(PersonTable, OfficialTable, NotaryTable, DocumentTable, CivilRegistryDocumentTable, RegisteringTable)
            }
        }

        @AfterAll
        @JvmStatic
        internal fun afterAll() {
            transaction {
                SchemaUtils.drop(PersonTable, OfficialTable, NotaryTable, DocumentTable, CivilRegistryDocumentTable, RegisteringTable)
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
            RegisteringDAO.removeWhere(Op.build { RegisteringTable.id neq null })
            CivilRegistryDocumentDAO.removeWhere(Op.build { CivilRegistryDocumentTable.id neq null })
            DocumentDAO.removeWhere(Op.build { DocumentTable.id neq null })
            NotaryDAO.removeWhere(Op.build { NotaryTable.id neq null })
            OfficialDAO.removeWhere(Op.build { OfficialTable.id neq null })
        }
    }

    @Test
    @DisplayName("Insert")
    fun testInsert(){
        val inserted = CivilRegistryDocumentDAO.insert(p).toType()

        assertEquals(p.id, inserted?.id)
        assertEquals(p.status, inserted?.status)
        assertEquals(p.notaryId, inserted?.notaryId)
        assertEquals(p.officialId, inserted?.officialId)
        assertEquals(p.registration, inserted?.registration)
        assertEquals(p.registering.size, inserted?.registering!!.size)

        p.registering.forEach {
            println(Json.encodeToString(it))
        }
        inserted!!.registering.forEach {
            println(Json.encodeToString(it))
        }

        inserted.registering.forEach {
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
    @DisplayName("Test select")
    fun testSelect() {
        val inserted = CivilRegistryDocumentDAO.insert(p).toType()
        val selected = CivilRegistryDocumentDAO.select(inserted!!.id!!)?.toType()

        assertEquals(p.id, selected?.id)
        assertEquals(p.status, selected?.status)
        assertEquals(p.notaryId, selected?.notaryId)
        assertEquals(p.officialId, selected?.officialId)
        assertEquals(p.registration, selected?.registration)

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
        val inserted = CivilRegistryDocumentDAO.insert(p).toType()!!
        val new = object : CivilRegistryDocument() {
            override var id: String? = inserted.id
            override val status: DocumentStatus = DocumentStatus.Invalid
            override val officialId: String = inserted.officialId
            override val notaryId: String = inserted.notaryId
            override val registration: String = "1111111111111111"
            override val registering: List<Registering> = listOf(
                Registering(inserted.registering.get(0).id, id!!, "Anotação 1 mudada"),
                Registering(inserted.registering.get(1).id, id!!, "Anotação 2"),
                Registering(null, id!!, "Anotação 3")
            )
        }

        new.registering.last().id = new.registering.last().createId()

        CivilRegistryDocumentDAO.update(new)

        val updated = CivilRegistryDocumentDAO.select(new.id!!)?.toType()

        assertEquals(inserted.id, updated?.id)
        assertEquals(inserted.officialId, updated?.officialId)
        assertEquals(inserted.notaryId, updated?.notaryId)
        assertEquals(inserted.registering.size, updated?.registering!!.size)
        assertNotEquals(inserted.status, updated?.status)

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
        val inserted = CivilRegistryDocumentDAO.insert(p).toType()!!

        CivilRegistryDocumentDAO.remove(inserted.id!!)

        val selected = CivilRegistryDocumentDAO.select(inserted.id!!)
        val registering = RegisteringDAO.selectMany(Op.build{RegisteringTable.documentID eq inserted.id})
        var count = 0
        transaction {
            count = registering.toList().size
        }

        assertEquals(null, selected)
        assertEquals(0,count)
    }
}