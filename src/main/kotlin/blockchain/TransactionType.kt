package blockchain

enum class TransactionType(val value:String) {
    Creation("Criação"),
    Devalidation("Desvalidação"),
    Registering("Averbação")
}