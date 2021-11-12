package authentication.logging

/**
 * Representa um tipo de ação efetuada no sistema
 */
enum class ActionType(val value: String) {
    Login("login"),
    Logout("logout"),
    SeeLogs("ver logs"),
    SeeLog("ver log"),
    SeeNotaries("ver cartórios"),
    SeeNotary("ver cartório"),
    AddNotary("criar cartório"),
    AddOfficialToNotary("adicionado funcionário ao cartório"),
    AddManagerToNotary("adicionado gerente ao cartório"),
    CreateAccount("criar conta de usuário"),
    SeeBirthCertificate("ver certidão de nascimento"),
    SeeBirthCertificates("ver certidões de nascimento"),
    CreateBirthCertificates("criar certidão de nascimento"),
    PrintBirthCertificates("Imprimir certidão de nascimento"),
    SeeNodes("ver nós"),
    SeeNode("ver nó"),
    CreateNode("criar nó"),
    SeeBlockchain("ver blockchain"),
    SeeBlock("ver bloco"),
    SeeDocumentTransactions("ver transações de documento"),
    CheckDocumentValidity("checar validade de documento"),

}