package authentication

/**
 * Representa o status de uma conta de usuário
 */
enum class UserStatus(val value: String) {
    Online("online"),
    Offline("offline"),
    Deactivated("desativada")
}