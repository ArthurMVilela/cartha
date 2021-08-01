package ui.features

import authentication.Permission
import authentication.Role
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*
import io.ktor.util.*
import io.ktor.util.pipeline.*
import ui.controllers.AuthenticationController
import ui.exception.AuthenticationFeatureException

class AuthenticationFeature(config: Configuration) {
    val authController = AuthenticationController()
    class Configuration() {}

    companion object Feature : ApplicationFeature<ApplicationCallPipeline, Configuration, AuthenticationFeature> {
        override val key = AttributeKey<AuthenticationFeature>("AuthenticationFeature")
        val AuthorizationPhase = PipelinePhase("Authorization")

        override fun install(
            pipeline: ApplicationCallPipeline,
            configure: Configuration.() -> Unit
        ): AuthenticationFeature {
            val configuration = Configuration().apply(configure)
            return AuthenticationFeature(configuration)
        }
    }

    fun interceptPipeline(pipeline: ApplicationCallPipeline, role: Role?, permission: Permission?) {
        pipeline.insertPhaseAfter(ApplicationCallPipeline.Features, Authentication.ChallengePhase)
        pipeline.insertPhaseAfter(Authentication.ChallengePhase, AuthorizationPhase)

        pipeline.intercept(AuthorizationPhase) {
            val principal = call.authentication.principal<UserSessionCookie>() ?: throw AuthenticationFeatureException("Usuário não logado")
            val session = authController.getSession(principal)

            if (!session.user.isAuthorized(role, permission)) {
                throw AuthenticationFeatureException("Usuário não pode acessar")
            }
        }
    }
}

class AuthorizedRouteSelector():RouteSelector(RouteSelectorEvaluation.qualityConstant) {
    override fun evaluate(context: RoutingResolveContext, segmentIndex: Int): RouteSelectorEvaluation {
        return RouteSelectorEvaluation.Constant
    }
}

fun Route.authorizedRoute(role: Role?, permission: Permission?, build: Route.() -> Unit): Route {
    var authorizedRoute = createChild(AuthorizedRouteSelector())
    application.feature(AuthenticationFeature).interceptPipeline(authorizedRoute, role, permission)
    authorizedRoute.build()
    return authorizedRoute
}