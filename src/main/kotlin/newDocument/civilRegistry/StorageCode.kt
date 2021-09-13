package newDocument.civilRegistry

/**
 * Código de acervo do documento de registro civil
 *
 * @property OwnStorage             Acervo Próprio
 * @property IncorporatedStorage    Acervo Incorporado
 */
enum class StorageCode(val value: String) {
    OwnStorage("01"),
    IncorporatedStorage("02")
}