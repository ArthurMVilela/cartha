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
import ui.features.AuthenticationMiddleware
import ui.features.UserSessionCookie
import ui.features.authorizedRoute
import ui.handlers.ErrorPagesHandlers
import ui.handlers.MainPageHandler
import ui.handlers.UserAccountHandler

fun main() {
    val mainPageHandler = MainPageHandler()
    val userAccountHandler = UserAccountHandler()
    val errorPageHandler = ErrorPagesHandlers()

    embeddedServer(Netty, port = 8080, watchPaths = listOf("templates", "js")) {
        install(StatusPages) {
            exception<Exception> { cause ->
                errorPageHandler.handleError(call, cause)
            }
            status(HttpStatusCode.NotFound) {
                errorPageHandler.handleError(call, NotFoundException())
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
        install(AuthenticationMiddleware)

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