package presentation

import authentication.Role
import authentication.controllers.AuthenticationController

class AuthenticationPresentationSetup {
    fun setupUsers() {
        val controller = AuthenticationController()

        controller.createUser(
            Role.SysAdmin,
            System.getenv("PRESENTATION_TEST_SYS_ADM_NAME")!!,
            System.getenv("PRESENTATION_TEST_SYS_ADM_EMAIL")!!,
            System.getenv("PRESENTATION_TEST_SYS_ADM_PASSWORD")!!,
            System.getenv("PRESENTATION_TEST_SYS_ADM_CPF")!!,
            null,
            null
        )
    }
}