package document.civilRegistry

import document.Document

/**
 * Representa um documento de registro civil
 *
 * @property registration                   Matrícula do documento
 * @property observationsAndRegistering     Observações e averbações
 */
abstract class CivilRegistryDocument():Document() {
    abstract val registration:String
    abstract val observationsAndRegistering:List<String>
}