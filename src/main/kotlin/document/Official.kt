package document

/**
 * Representa um oficial(tabelião/funcionário) que autentifica um documento
 */
class Official(
    override val id: String,
    override val name: String,
    override val cpf: String,
    override val sex: Sex
): Person(id, name, cpf, sex)