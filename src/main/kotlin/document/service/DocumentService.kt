@file:JvmName("DocumentService")

package document.service

import document.handlers.civilRegistry.birth.BirthCertificateHandler
import document.handlers.civilRegistry.marriage.MarriageCertificateHandler
import document.handlers.DocumentHandler
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
import presentation.DocumentPresentationSetup

fun main(args: Array<String>) {
    try {
        DatabaseInitializer.loadConfigurations()
        DatabaseInitializer.initialize()
    } catch (e:Exception) {
        e.printStackTrace()
        exitProcess(1)
    }


    if(args.contains("--presentation-test")) {
        try {
            DocumentPresentationSetup().setupNotary()
        } catch (ex: Exception) {
            println("Erro ao tentar aplicar preparativos para apresentação")
        }
    }

    val notaryHandler = NotaryHandler()
    val personHandler = PersonHandler()
    val birthCertificateHandler = BirthCertificateHandler()
    val marriageCertificateHandler = MarriageCertificateHandler()
    val documentHandler = DocumentHandler()

    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            json()
        }
        install(StatusPages) {
            exception<Throwable> { cause ->
                cause.printStackTrace()
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
                    get("/notary/{id}") {
                        personHandler.getOfficials(call)
                    }
                }
            }
            route("/document") {
                get("/{id}") {
                    documentHandler.getDocumentById(call)
                }
                route("/civil_registry") {
                    route("/birth") {
                        post(""){
                            birthCertificateHandler.createBirthCertificate(call)
                        }
                        get("/{id}") {
                            birthCertificateHandler.getBirthCertificate(call)
                        }
                        get("/cpf/{cpf}") {
                            birthCertificateHandler.getBirthCertificateByCpf(call)
                        }
                        get("/affiliation/{cpf}") {
                            birthCertificateHandler.getBirthCertificatesWithAffiliation(call)
                        }
                        get("/official/{id}") {
                            birthCertificateHandler.getBirthCertificatesByOfficial(call)
                        }
                        get("/notary/{id}") {
                            birthCertificateHandler.getBirthCertificatesByNotary(call)
                        }
                    }
                    route("/marriage") {
                        post("") {
                            marriageCertificateHandler.createMarriageCertificate(call)
                        }
                        get("/{id}") {
                            marriageCertificateHandler.getMarriageCertificate(call)
                        }
                    }
                }
            }
        }
    }.start(true)
}