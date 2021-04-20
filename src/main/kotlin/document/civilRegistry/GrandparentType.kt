package document.civilRegistry

/**
 * Tipo enumerado definindo o tipo de avó/avô (Materno/Paterno)
 *
 * @property Paternal       Avó/avô paterno
 * @property Maternal       Avó/avô materno
 */
enum class GrandparentType(val value:String) {
    Paternal("paterno"),
    Maternal("materno")
}