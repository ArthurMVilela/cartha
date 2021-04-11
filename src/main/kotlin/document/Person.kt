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
class Person (
    val id: String,
    val name: String,
    val cpf: String,
    val sex: Sex
)