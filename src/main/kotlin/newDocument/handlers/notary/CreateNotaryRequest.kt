package newDocument.handlers.notary

import kotlinx.serialization.Serializable

@Serializable
class CreateNotaryRequest(
    val name:String,
    val cnpj:String,
    val cns:String
) {
}