@file:JvmName("UIService")

package ui.service

import freemarker.cache.ClassTemplateLoader
import freemarker.core.HTMLOutputFormat
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.content.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ui.handlers.UserAccountHandler

fun main() {
    val userAccountHandler = UserAccountHandler()

    embeddedServer(Netty, port = 8081, watchPaths = listOf("templates", "js")) {
        install(FreeMarker) {
            templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
            outputFormat = HTMLOutputFormat.INSTANCE
        }
        routing {
            static("static") {
                resources("js")
            }
            get("/login") {
                userAccountHandler.loginPage(call)
            }
            post("/login") {
                userAccountHandler.postLogin(call)
            }
        }
    }.start(true)
}