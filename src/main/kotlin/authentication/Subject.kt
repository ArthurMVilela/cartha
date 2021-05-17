package authentication

/**
 * Representa um assunto para o controle de acesso
 */
enum class Subject(val value:String) {
    UserAccount("conta de usuário"),
    Blockchain("blockchain"),
    Notaries("cartórios"),
    Notary("cartório"),
    PersonalDocument("documentos pessoais"),
    CivilRegistry("Registro Cívil")
}