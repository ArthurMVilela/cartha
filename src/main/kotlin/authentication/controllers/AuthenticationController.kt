package authentication.controllers

import authentication.logging.controllers.AccessLogController

class AuthenticationController {
    private val accessLogController = AccessLogController()
    private val userController = UserController()
}