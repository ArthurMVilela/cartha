@file:JvmName("UIService")

import blockchain.Block
import blockchain.Blockchain
import blockchain.Transaction
import blockchain.TransactionType
import freemarker.cache.*
import freemarker.core.*
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ui.EnumMaps
import ui.UIService
import java.time.LocalDateTime

fun main() {
    val service = UIService(
        nodeManagerURL = System.getenv("NODE_MANAGER_URL"),
        authenticationURL = System.getenv("AUTHENTICATION_URL")
    )
    embeddedServer(Netty, port = 8080) {
        install(FreeMarker) {
            templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
            outputFormat = HTMLOutputFormat.INSTANCE
        }
        routing {
            static("static") {
                resources("js")
            }
            get("/physical_person") {
                val data = mapOf(
                    "sex" to EnumMaps.sex,
                    "month" to EnumMaps.month,
                    "civilStatus" to EnumMaps.civilStatus,
                    "color" to EnumMaps.color
                )

                call.respond(FreeMarkerContent("physical-person.ftl", data))
            }
            get("/notary") {
                call.respond(FreeMarkerContent("notary.ftl", null))
            }
            get("/official") {
                val data = mapOf(
                    "sex" to EnumMaps.sex,
                )

                call.respond(FreeMarkerContent("official.ftl", data))
            }
            get("/blockchain") {
                service.getBlockChain(call)
            }
            get("/blockchain/{notaryId}") {
                service.getBlockChain(call)
            }
            get("/blocks/{nodeId}/{blockId}") {
                service.getBlock(call)
            }
        }
    }.start(true)
}