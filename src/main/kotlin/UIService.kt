@file:JvmName("UIService")

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

fun main() {
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
        }
    }.start(true)
}