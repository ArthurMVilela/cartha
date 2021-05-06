package ui

import authentication.Role
import authentication.UserSession
import blockchain.Block
import blockchain.Blockchain
import blockchain.network.Node
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.freemarker.*
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

    suspend fun getUserSession(call: ApplicationCall): UserSession? {
        val cookie = call.sessions.get<UserSessionCookie>() ?: return null

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

    suspend fun getBlockChain(call:ApplicationCall) {
        val notaryId = call.parameters["notaryId"]
        val selectedId:String

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


        val data = mapOf(
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