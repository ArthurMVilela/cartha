@file:JvmName("UIService")

package ui.service

import authentication.Permission
import authentication.Role
import authentication.Subject
import authentication.logging.AccessLogSearchFilter
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
import ui.features.*
import ui.handlers.*
import java.util.*

fun main() {
    val mainPageHandler = MainPageHandler()
    val userAccountHandler = UserAccountHandler()
    val accessLogsHandlers = AccessLogsHandlers()
    val errorPageHandler = ErrorPageHandler()
    val blockchainHandlers = BlockchainHandlers()
    val notaryHandler = NotaryHandler()
    val birthCertificateHandler = BirthCertificateHandler()

    embeddedServer(Netty, port = 8080, watchPaths = listOf("templates", "js")) {
        install(StatusPages) {
            exception<Exception> { cause ->
                cause.printStackTrace()
                errorPageHandler.handleError(call, cause)
            }
            status(HttpStatusCode.NotFound) {
                errorPageHandler.handleError(call, NotFoundException())
            }
        }

        //Prepara para os cookies que serão usados para controlar sessões de usuário
        install(Sessions) {
            cookie<UserSessionCookie>("user_session", SessionStorageMemory())
            cookie<AccessLogSearchFilter>("accessLogSearchFilter", SessionStorageMemory())
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

            get("/create-account") {
                userAccountHandler.getCreateClientPage(call)
            }
            post("/create-account") {
                userAccountHandler.createClient(call)
            }

            authenticate {
                authorizedRoute(Role.Client) {
                    get("/document") {
                        call.respond("Hello")
                    }
                }
                authorizedRoute(Role.SysAdmin) {
                    get("/logs") {
                        accessLogsHandlers.getLogs(call)
                    }
                    post("/logs") {
                        accessLogsHandlers.postGetLogs(call)
                    }
                    get("/logs/{id}") {
                        accessLogsHandlers.getLog(call)
                    }
                    route("/blockchain") {
                        get("") {
                            blockchainHandlers.getBlockchainPage(call)
                        }
                        get("/nodes") {
                            blockchainHandlers.getNodesPage(call)
                        }
                        get("/blocks") {
                            blockchainHandlers.getBlocksPage(call)
                        }
                        get("/blocks/{nodeId}") {
                            blockchainHandlers.getBlocksPage(call)
                        }
                        get("/blocks/{nodeId}/{blockId}") {
                            blockchainHandlers.getBlockPage(call)
                        }
                    }
                }

                route("/notary") {
                    authorizedRoute(Role.SysAdmin) {
                        get("/create") {
                            notaryHandler.getCreateNotaryPage(call)
                        }

                        get("/{id}/add-manager") {
                            userAccountHandler.getCreateManagerPage(call)
                        }

                        post("/{id}/add-manager") {
                            userAccountHandler.createManager(call)
                        }
                        post("/create") {
                            notaryHandler.createNotary(call)
                        }
                    }
                    authorizedRoute(Role.SysAdmin, Role.Manager) {
                        get("") {
                            if (call.getUserRole() == Role.Manager) {
                                val id = call.getUserPermissions()
                                    .first { it.subject == Subject.Notary && it.domainId != null }.domainId
                                call.respondRedirect("/notary/${id}")
                            }
                            notaryHandler.getNotariesPage(call)
                        }
                        get("/{id}") {
                            if (call.getUserRole() == Role.Manager) {
                                val id = UUID.fromString(call.parameters["id"])
                                call.checkForPermission(Permission(null, Subject.Notary, id))
                            }
                            notaryHandler.getNotaryPage(call)
                        }
                        get("/{id}/add-official") {
                            if (call.getUserRole() == Role.Manager) {
                                val id = UUID.fromString(call.parameters["id"])
                                call.checkForPermission(Permission(null, Subject.Notary, id))
                            }
                            userAccountHandler.getCreateOfficialPage(call)
                        }
                        post("/{id}/add-official") {
                            if (call.getUserRole() == Role.Manager) {
                                val id = UUID.fromString(call.parameters["id"])
                                call.checkForPermission(Permission(null, Subject.Notary, id))
                            }
                            userAccountHandler.createOfficial(call)
                        }
                    }
                }

                route("/civil-registry") {
                    route("/birth") {
                        get("") {

                        }
                        authorizedRoute(Role.Manager, Role.Official) {
                            get("/create") {
                                birthCertificateHandler.getCreateBirthCertificatePage(call)
                            }
                            post("/create") {
                                birthCertificateHandler.createBirthCertificate(call)
                            }
                        }
                        get("/official/{id}") {
                            call.respond("Mostrar certidões de nascimento feitas por este funcionário")
                        }
                        get("/notary/{id}") {
                            call.respond("Mostrar certidões de nascimento feitas neste cartório")
                        }
                        get("/{id}") {
                            call.respond("Mostrar certidão de nascimento com este id")
                        }
                        get("/person/{id}") {
                            call.respond("Mostrar certidões de nascimento feitas deste cliente")
                        }
                    }
                }

            }

        }
    }.start(true)
}