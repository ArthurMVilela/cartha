package document

/**
 * Representa um oficial(tabelião/funcionário) que autentifica um documento
 */
class Official(
//    override val id: String,
    override val name: String,
    override val cpf: String,
    override val sex: Sex
): Person(name, cpf, sex) {
    private var id:String = ""
        get() = field
        set(value) {field = value}

    constructor(id: String,
                name: String,
                cpf: String,
                sex: Sex):this(name,cpf,sex) {
        this.id = id
    }
}