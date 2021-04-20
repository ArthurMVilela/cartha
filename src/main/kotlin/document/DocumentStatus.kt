package document

/**
 * Tipo enumerado que representa o estado de um documento
 *
 * @property Valid      Documento válido
 * @property Invalid    Documento inválido
 */
enum class DocumentStatus(val value:String) {
    Valid("válido"),
    Invalid("inválido")
}