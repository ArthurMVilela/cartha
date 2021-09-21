package document.civilRegistry.marriage
//(https://www.angloinfo.com/how-to/brazil/family/marriage-partnerships/marital-contracts)

/**
 * Representa o regime de bens do matrímonio
 *
 * @property PartialPropertyRuling          Comunhão Parcial
 * @property CommunityPropertyRuling        Comunhão de Bens
 * @property SeparatePropertyRuling         Separação de Bens
 * @property FinalPartitionOfAcquisitions   Participação Final nos aquetos
 */
enum class MatrimonialRegime(val value:String) {
    PartialPropertyRuling("Comunhão Parcial"),
    CommunityPropertyRuling("Comunhão de Bens"),
    SeparatePropertyRuling("Separação de Bens"),
    FinalPartitionOfAcquisitions("Participação Final nos aquetos")
}