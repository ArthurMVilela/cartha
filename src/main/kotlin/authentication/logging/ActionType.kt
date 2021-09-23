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
    CreateAccount("criar conta de usuário")

}