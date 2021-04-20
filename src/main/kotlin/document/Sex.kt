package document

/**
 * Tipo enumerado que representa o sexo de uma pessoa
 *
 * @property Male       sexo masculino
 * @property Female     sexo feminino
 */
enum class Sex(val value: String) {
    Male("Masculino"),
    Female("Feminino")
}