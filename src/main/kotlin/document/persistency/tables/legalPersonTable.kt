package document.persistency.tables

import org.jetbrains.exposed.sql.Table

object legalPersonTable : Table("legal_person") {
    val id = char("id", 32).references(personTable.id)
    val cpnj = char("cnpj", 14)

    override val primaryKey = PrimaryKey(physicalPersonTable.id, name = "pk_legal_person_id")
}