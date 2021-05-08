package authentication

/**
 * Representa uma função/cargo de usuário para o controle de acesso
 */
enum class Role(val value: String) {
    Client("cliente"),
    Official("oficial"),
    Manager("administrador(a) de cartório"),
    SysAdmin("administrador(a) de sistema")
}