@file:JvmName("UIService")

import document.PhysicalPerson
import document.Sex
import document.service.BadRequestException
import freemarker.cache.*
import freemarker.core.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ui.EnumMaps
import java.time.Month
import java.util.*

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
        }
    }.start(true)
}