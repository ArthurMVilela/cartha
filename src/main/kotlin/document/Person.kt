package document

import kotlinx.serialization.Serializable

/**
 * Representa uma pessoa física que se relaciona a documentos
 *
 * @property id     identificador único
 * @property name   nome do indivíduo
 */
@Serializable
abstract class Person (
) {
    abstract var id: String?
    abstract val name: String
}