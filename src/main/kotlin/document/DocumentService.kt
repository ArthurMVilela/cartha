package document

import document.controllers.ControllersFacade
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*

class DocumentService {
    val controller = ControllersFacade()
}