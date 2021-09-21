@file:JvmName("DocumentService")

package document.service

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import document.handlers.notary.NotaryHandler
import document.handlers.person.PersonHandler
import document.persistence.DatabaseInitializer
import kotlin.system.exitProcess

fun main() {
    try {
        DatabaseInitializer.loadConfigurations()
        DatabaseInitializer.initialize()
    } catch (e:Exception) {
        e.printStackTrace()
        exitProcess(1)
    }

    val notaryHandler = NotaryHandler()
    val personHandler = PersonHandler()

    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            json()
        }
        install(StatusPages) {
            exception<Throwable> { cause ->
                call.respond(HttpStatusCode.InternalServerError, "Erro inexperado ocorreu.")
            }
            exception<BadRequestException> { cause ->
                call.respond(HttpStatusCode.BadRequest, cause.message?:"Erro inexperado ocorreu.")
            }
            exception<NotFoundException> { cause ->
                call.respond(HttpStatusCode.NotFound, cause.message?:"Erro inexperado ocorreu.")
            }
        }
        routing {
            route("/notary") {
                post("") {
                    notaryHandler.createNotary(call)
                }
                get("") {
                    notaryHandler.getNotaries(call)
                }
                get("/{id}") {
                    notaryHandler.getNotary(call)
                }
                get("/cnpj/{cnpj}") {
                    notaryHandler.getNotary(call)
                }
            }
            route("/person") {
                route("/physical_person") {
                    post("") {
                        personHandler.createPhysicalPerson(call)
                    }
                    get("/{id}") {
                        personHandler.getPhysicalPerson(call)
                    }
                    get("/cpf/{cpf}") {
                        personHandler.getPhysicalPerson(call)
                    }
                }
                route("/official") {
                    post("") {
                        personHandler.createOfficial(call)
                    }
                    get("/{id}") {
                        personHandler.getOfficial(call)
                    }
                    get("/cpf/{cpf}") {
                        personHandler.getOfficial(call)
                    }
                }
            }
        }
    }.start(true)
}