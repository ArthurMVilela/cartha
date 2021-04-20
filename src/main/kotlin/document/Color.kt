package document

/**
 * Representa a cor/raça de um indivíduo de acordo com o IBGE
 * @property White          Branco(a)
 * @property Black,         Preto(a)
 * @property Pardo,         Pardo(a)
 * @property Yellow,        Amarelo(a)
 * @property Indigenous     Indígena
 */
enum class Color(val value:String) {
    White("Branco(a)"),
    Black("Preto(a)"),
    Pardo("Pardo(a)"),
    Yellow("Amarelo(a)"),
    Indigenous("Indígena")
}