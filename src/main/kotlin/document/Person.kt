package document

import kotlinx.serialization.Serializable

/**
 * Representa uma pessoa física que se relaciona a documentos
 *
 * @property id     identificador único
 * @property name   nome do indivíduo
 * @property cpf    cpf do indivíduo
 * @property sex    sexo do indivíduo
 */
@Serializable
open class Person (
//    open val id: String,
    open val name: String,
    open val cpf: String,
    open val sex: Sex
) {
    private var id:String = ""
        get() = field
        set(value) {field = value}

    constructor(id: String,
                name: String,
                cpf: String,
                sex: Sex):this(name, cpf, sex) {
                    this.id = id
                }
}