package authentication

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

internal class UserTest {
    @Test
    internal fun testUUID() {
        val userA = User.createClient("Fulano", "fulano@gmail.com",  "1234")
        val userB = User.createClient("Fulano", "fulano@gmail.com",  "1234")

        assertNotEquals(userA.id, userB.id)
        assertDoesNotThrow(fun () {
            UUID.fromString(userA.id.toString())
        })
    }
}