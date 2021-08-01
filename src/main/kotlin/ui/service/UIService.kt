@file:JvmName("UIService")

package ui.service

import authentication.Role
import freemarker.cache.ClassTemplateLoader
import freemarker.core.HTMLOutputFormat
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.sessions.*
import ui.exception.AuthenticationFeatureException
import ui.features.AuthenticationFeature
import ui.features.UserSessionCookie
import ui.features.authorizedRoute
import ui.handlers.MainPageHandler
import ui.handlers.UserAccountHandler

fun main() {
    val mainPageHandler = MainPageHandler()
    val userAccountHandler = UserAccountHandler()

    embeddedServer(Netty, port = 8080, watchPaths = listOf("templates", "js")) {
        install(StatusPages) {
            exception<AuthenticationFeatureException> {
                call.respond(HttpStatusCode.Forbidden, "Acesso Negado.")
            }
        }

        //Prepara para os cookies que serão usados para controlar sessões de usuário
        install(Sessions) {
            cookie<UserSessionCookie>("user_session", SessionStorageMemory())
        }
        install(Authentication) {
            session<UserSessionCookie> {
                validate { session : UserSessionCookie ->
                    session
                }
                challenge {
                    call.respondRedirect("/login")
                }
            }
        }
        install(AuthenticationFeature)

        //FreeMaker renderiza templates em HTML que é servido no 
        install(FreeMarker) {
            templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
            outputFormat = HTMLOutputFormat.INSTANCE
        }
        routing {
            static("static") {
                resources("js")
            }
            get("/") {
                mainPageHandler.mainPage(call)
            }
            get("/login") {
                userAccountHandler.loginPage(call)
            }
            post("/login") {
                userAccountHandler.postLogin(call)
            }
            get("/logout") {
                userAccountHandler.logout(call)
            }

            authenticate {
                authorizedRoute(Role.Client, null) {
                    get("/document") {
                        call.respond("Hello")
                    }
                }
                authorizedRoute(Role.SysAdmin, null) {
                    get("/blockchain") {
                        call.respond("Hello")
                    }
                }

            }
        }
    }.start(true)
}