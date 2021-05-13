package authentication.logging

import authentication.Subject
import kotlinx.serialization.Serializable
import util.serializer.UUIDSerializer
import java.util.*

/**
 * Representa uma ação efetuada no sistema
 *
 * @param type          tipo de ação
 * @param subject       assunto da ação
 * @param domainId      id do dominio relacionado à ação
 */
@Serializable
class Action (
    val type: ActionType,
    val subject: Subject,
    @Serializable(with = UUIDSerializer::class)
    val domainId: UUID?
) {
}