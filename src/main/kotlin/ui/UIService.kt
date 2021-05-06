package ui

import authentication.Role
import authentication.Subject
import authentication.UserSession
import blockchain.Block
import blockchain.Blockchain
import blockchain.network.Node
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.sessions.*
import kotlinx.coroutines.runBlocking

class UIService (
    val nodeManagerURL:String,
    val authenticationURL:String
) {

    val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    suspend fun isAuthorised(call: ApplicationCall, role: Role?, subject: Subject?, domainId: String?):Boolean {
        val session = getUserSession(call)?:return false
        return session.isAuthorized(role, subject, domainId)
    }

    suspend fun getUserSession(call: ApplicationCall): UserSession? {
        val cookie = call.sessions.get<UserSessionCookie>() ?: return null

        return getUserSession(cookie)
    }

    suspend fun getUserSession(cookie: UserSessionCookie): UserSession? {
        return try {
            val session = runBlocking {
                return@runBlocking client.get<UserSession>() {
                    url("$authenticationURL/session/${cookie.sessionId}")
                }
            }
            session
        } catch (ex: Exception) {
            null
        }
    }

    fun getMenu(role: Role?):Map<String, String> {
        return when(role) {
            null -> Menus.anonimous
            Role.Client -> Menus.client
            Role.Official -> Menus.official
            Role.Manager -> Menus.manager
            Role.SysAdmin -> Menus.sysAdmin
        }
    }

    suspend fun getMainPage(call: ApplicationCall) {
        val userSession = getUserSession(call)
        val menu = getMenu(userSession?.userRole)

        val data = mapOf(
            "userRole" to userSession?.userRole,
            "menu" to menu
        )

        call.respond(FreeMarkerContent("main.ftl", data))
    }

    suspend fun getLogin(call: ApplicationCall) {
        val userSession = getUserSession(call)

        if (userSession?.end != null) {
            call.respondRedirect("/")
            return
        }

        val menu = getMenu(null)

        val data = mapOf(
            "userRole" to null,
            "menu" to menu
        )

        call.respond(FreeMarkerContent("login.ftl", data))
    }

    suspend fun postLogin(call: ApplicationCall) {
        val parameters = call.receiveParameters()

        if (parameters["email"].isNullOrEmpty() && parameters["cpf"].isNullOrEmpty()) {
            throw BadRequestException("Email ou cpf deve não ser nulo")
        }

        if (parameters["password"].isNullOrEmpty()) {
            throw BadRequestException("Senha não pode ser nula ou vázia")
        }

        val session = try {
            runBlocking {
                return@runBlocking client.submitForm<UserSession>(
                    url = "$authenticationURL/login",
                    formParameters = Parameters.build {
                        parameters["email"]?.let { append("email", it) }
                        parameters["cpf"]?.let { append("cpf", it) }
                        append("password", parameters["password"]!!)
                    }
                )
            }
        } catch (ex: Exception) {
            throw ex
        }

        call.sessions.set("USER_SESSION", UserSessionCookie(session.id!!))
        call.respondRedirect("/")
    }

    suspend fun getClientUserPage(call: ApplicationCall) {
        val userSession = getUserSession(call)
        val menu = getMenu(userSession?.userRole)

        val data = mapOf(
            "userRole" to userSession?.userRole,
            "menu" to menu,
            "sex" to EnumMaps.sex,
            "month" to EnumMaps.month,
            "civilStatus" to EnumMaps.civilStatus,
            "color" to EnumMaps.color
        )

        call.respond(FreeMarkerContent("create-client.ftl", data))
    }
    suspend fun createClientUser(call: ApplicationCall) {}

    suspend fun logout(call: ApplicationCall) {
        val session = getUserSession(call)?:throw BadRequestException("Nenhuma sessão encontrada")
        if (session.end != null) {
            throw BadRequestException("Sessão já encerrada")
        }

        try {
            client.post<Any>("$authenticationURL/logout/${session.id}")
        } catch (ex: Exception) {
            throw ex
        }

        call.sessions.clear("USER_SESSION")

        call.respondRedirect("/")
    }

    suspend fun getBlockChain(call:ApplicationCall) {
        val notaryId = call.parameters["notaryId"]
        val selectedId:String

        if (!isAuthorised(call, Role.SysAdmin, Subject.Blockchain, null)) {
            throw BadRequestException("Accesso negado")
        }

        val nodes:List<Node>
        runBlocking {
            nodes = client.get {
                url("$nodeManagerURL/nodes")
            }
        }

        if (notaryId.isNullOrEmpty()) {
            selectedId = nodes.first().id!!
        } else {
            selectedId = notaryId
        }

        val selectedNode = nodes.first { node -> node.id == selectedId }
        val blockchain:Blockchain

        runBlocking {
            blockchain = client.get {
                url("${selectedNode.address}/blockchain")
            }
        }

        val userSession = getUserSession(call)
        val menu = getMenu(userSession?.userRole)

        val data = mapOf(
            "userRole" to userSession?.userRole,
            "menu" to menu,
            "nodes" to nodes,
            "selected" to selectedId,
            "blockchain" to blockchain,
        )

        call.respond(FreeMarkerContent("blockchain.ftl", data))
    }
    suspend fun getBlock(call: ApplicationCall) {
        val blockId = call.parameters["blockId"]
        val nodeId = call.parameters["nodeId"]

        val nodes:List<Node>
        runBlocking {
            nodes = client.get {
                url("$nodeManagerURL/nodes")
            }
        }

        val node = nodes.first {node -> node.id == nodeId }
        val block:Block
        runBlocking {
            block = client.get {
                url("${node.address}/blocks/$blockId")
            }
        }

        val data = mapOf(
            "block" to block
        )

        call.respond(FreeMarkerContent("block.ftl", data))
    }
}