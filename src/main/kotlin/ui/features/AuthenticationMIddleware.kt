package ui.features

import authentication.Permission
import authentication.Role
import authentication.Subject
import authentication.User
import authentication.exception.UserSessionNotFound
import authentication.logging.ActionType
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.util.*
import io.ktor.util.pipeline.*
import ui.controllers.AuthenticationController
import ui.controllers.DocumentController
import ui.exception.AuthenticationMiddlewareException
import java.util.*
import kotlin.collections.HashSet

class AuthenticationMiddleware(config: Configuration) {
    val authController = AuthenticationController()
    class Configuration() {}

    companion object Feature : ApplicationFeature<ApplicationCallPipeline, Configuration, AuthenticationMiddleware> {
        override val key = AttributeKey<AuthenticationMiddleware>("AuthenticationFeature")
        val AuthorizationPhase = PipelinePhase("Authorization")

        override fun install(
            pipeline: ApplicationCallPipeline,
            configure: Configuration.() -> Unit
        ): AuthenticationMiddleware {
            val configuration = Configuration().apply(configure)
            return AuthenticationMiddleware(configuration)
        }
    }

    fun interceptPipeline(pipeline: ApplicationCallPipeline, roles: Array<out Role?>) {
        pipeline.insertPhaseAfter(ApplicationCallPipeline.Features, Authentication.ChallengePhase)
        pipeline.insertPhaseAfter(Authentication.ChallengePhase, AuthorizationPhase)

        pipeline.intercept(AuthorizationPhase) {
            val principal = call.authentication.principal<UserSessionCookie>() ?: throw AuthenticationMiddlewareException("Usuário não logado")
            val session = authController.getSession(principal)

            if (!roles.contains(session.user.role)) {
                throw AuthenticationMiddlewareException("Usuário não pode acessar")
            }
        }
    }
}

class AuthorizedRouteSelector():RouteSelector(RouteSelectorEvaluation.qualityConstant) {
    override fun evaluate(context: RoutingResolveContext, segmentIndex: Int): RouteSelectorEvaluation {
        return RouteSelectorEvaluation.Constant
    }
}

fun Route.authorizedRoute(vararg roles: Role?, build: Route.() -> Unit): Route {
    var authorizedRoute = createChild(AuthorizedRouteSelector())
    application.feature(AuthenticationMiddleware).interceptPipeline(authorizedRoute, roles)
    authorizedRoute.build()
    return authorizedRoute
}

suspend fun ApplicationCall.logAction(actionType: ActionType, subject: Subject, domainId: UUID?) {
    val authController = AuthenticationController()
    val sessionCookie = sessions.get<UserSessionCookie>()?:throw UserSessionNotFound("Usuário não logado.")
    authController.logAction(sessionCookie, actionType, subject, domainId)
}

suspend fun ApplicationCall.getUserRole(): Role? {
    val authController = AuthenticationController()
    return try {
        authController.getUserRole(sessions.get<UserSessionCookie>()!!)
    } catch (ex: Exception) {
        null
    }
}

suspend fun ApplicationCall.checkForPermission(permission: Permission) {
    val authController = AuthenticationController()
    val sessionCookie = sessions.get<UserSessionCookie>()?:throw UserSessionNotFound("Usuário não logado.")
    val session = authController.getSession(sessionCookie)

    if (!session.user.isAuthorized(session.user.role, permission)) {
        throw AuthenticationMiddlewareException("Usuário não pode acessar")
    }
}

suspend fun ApplicationCall.getUserPermissions(): HashSet<Permission> {
    val authController = AuthenticationController()
    val sessionCookie = sessions.get<UserSessionCookie>()?:throw UserSessionNotFound("Usuário não logado.")
    val session = authController.getSession(sessionCookie)

    return session.user.permissions
}

suspend fun ApplicationCall.getUserId(): UUID {
    val authController = AuthenticationController()
    val sessionCookie = sessions.get<UserSessionCookie>()?:throw UserSessionNotFound("Usuário não logado.")
    val session = authController.getSession(sessionCookie)

    return session.user.id
}

suspend fun ApplicationCall.getUserOfficialId(): UUID {
    val userId = getUserId()
    val userNotary = getUserPermissions()
        .first { it.subject == Subject.CivilRegistry && it.domainId != null }.domainId
    val documentController = DocumentController()
    val official = documentController.getOfficials(userNotary!!).first{
        it.accountId == userId
    }

    return official.id
}