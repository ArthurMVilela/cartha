package document

/**
 * Tipo enumerado que representa o estado de um documento
 *
 * @property Valid                  Documento válido
 * @property Invalid                Documento inválido
 * @property WaitingValidation      Documento esperando validação
 */
enum class DocumentStatus(val value:String) {
    Valid("Válido"),
    Invalid("Inválido"),
    WaitingValidation("Esperando validação")
}