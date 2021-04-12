package document.civilRegistry

import document.Document

/**
 * Representa um documento de registro civil
 *
 * @property registration       Matrícula do documento
 */
abstract class CivilRegistryDocument():Document() {
    abstract val registration:String
}