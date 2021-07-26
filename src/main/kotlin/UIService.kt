//@file:JvmName("UIService")
//
//import authentication.Role
//import blockchain.Block
//import blockchain.Blockchain
//import blockchain.Transaction
//import blockchain.TransactionType
//import freemarker.cache.*
//import freemarker.core.*
//import io.ktor.application.*
//import io.ktor.auth.*
//import io.ktor.client.engine.*
//import io.ktor.features.*
//import io.ktor.freemarker.*
//import io.ktor.http.content.*
//import io.ktor.response.*
//import io.ktor.routing.*
//import io.ktor.server.engine.*
//import io.ktor.server.netty.*
//import io.ktor.sessions.*
//import ui.EnumMaps
//import ui.UIService
//import ui.features.UserSessionCookie
//import java.time.LocalDateTime
//
//fun main() {
//    val nodeManagerURL = System.getenv("NODE_MANAGER_URL")
//    val authenticationURL = System.getenv("AUTHENTICATION_URL")
//
//    println("$nodeManagerURL $authenticationURL")
//
//    val service = UIService(
//        nodeManagerURL = nodeManagerURL,
//        authenticationURL = authenticationURL
//    )
//    embeddedServer(Netty, port = 8081) {
//        install(Sessions) {
//            cookie<UserSessionCookie>("USER_SESSION"){
//                cookie.path = "/"
//            }
//        }
//        install(Authentication) {
//            session<UserSessionCookie>() {
//                challenge {
//                    call.respondRedirect("/login")
//                }
//                validate { session ->
//                    val userSession = service.getUserSession(session)?: return@validate null
//
//                    if (userSession.end != null) {
//
//                        return@validate null
//                    }
//
//                    session
//                }
//            }
//        }
//        install(FreeMarker) {
//            templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
//            outputFormat = HTMLOutputFormat.INSTANCE
//        }
//        routing {
//            static("static") {
//                resources("js")
//            }
//            get("/") {
//                service.getMainPage(call)
//            }
//
//            get("/login") {
//                service.getLogin(call)
//            }
//            post("/login") {
//                service.postLogin(call)
//            }
//            get("/logout") {
//                service.logout(call)
//            }
//            get("/create-account/client") {
//                service.getClientUserPage(call)
//            }
//            post("/create-account/client") {
//                service.createClientUser(call)
//            }
//
//            authenticate {
//                get("/physical_person") {
//                    val data = mapOf(
//                        "sex" to EnumMaps.sex,
//                        "month" to EnumMaps.month,
//                        "civilStatus" to EnumMaps.civilStatus,
//                        "color" to EnumMaps.color
//                    )
//
//                    call.respond(FreeMarkerContent("physical-person.ftl", data))
//                }
//                get("/notary") {
//                    service.getNotaryPage(call)
//                }
//                get("/official") {
//                    val data = mapOf(
//                        "sex" to EnumMaps.sex,
//                    )
//
//                    call.respond(FreeMarkerContent("official.ftl", data))
//                }
//                get("/blockchain") {
//                    service.getBlockChain(call)
//                }
//                get("/blockchain/{notaryId}") {
//                    service.getBlockChain(call)
//                }
//                get("/blocks/{nodeId}/{blockId}") {
//                    service.getBlock(call)
//                }
//            }
//        }
//    }.start(true)
//}