package newDocument.person

/**
 * Classe enumarada que representa o estado cívil de um indivíduo
 *
 * @property Married        Casado(a)
 * @property Single         Solteiro(a)
 * @property Divorced       Divorciado(a)
 * @property Widowed        Viúvo(a)
 */
enum class CivilStatus(val value:String) {
    Married("Casado(a)"),
    Single("Solteiro(a)"),
    Divorced("Divorciado(a)"),
    Widowed("Viúvo(a)")
}