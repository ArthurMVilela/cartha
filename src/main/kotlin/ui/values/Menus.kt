package ui.values

object Menus {
    val anonimous = mapOf(
        "Início" to "/",
    )
    val client = mapOf(
        "Início" to "/",
        "Documentos" to "/document"
    )
    val official = mapOf(
        "Início" to "/",
        "Registro cívil" to "/civil-registry"
    )
    val manager = mapOf(
        "Início" to "/",
        "Registro cívil" to "/civil-registry",
        "Cartório" to "/notary"
    )
    val sysAdmin = mapOf(
        "Início" to "/",
        "Blockchain" to "/blockchain",
        "Logs" to "/logs",
        "Cartórios" to "/notary"
    )
}