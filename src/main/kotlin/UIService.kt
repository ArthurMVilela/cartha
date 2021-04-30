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
    val service = UIService()
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
            get("/block") {
                val block = Block(
                    LocalDateTime.now(),
                    listOf(
                        Transaction(LocalDateTime.now(), "1111", "-------", TransactionType.Creation),
                        Transaction(LocalDateTime.now(), "2222", "-------", TransactionType.Creation),
                        Transaction(LocalDateTime.now(), "1111", "------1", TransactionType.Registering)
                    ),
                    "11111",
                    ""
                )

                val data = mapOf(
                    "block" to block,
                )
                call.respond(FreeMarkerContent("block.ftl", data))
            }
        }
    }.start(true)
}