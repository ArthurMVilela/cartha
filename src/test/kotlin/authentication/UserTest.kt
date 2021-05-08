package authentication

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
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

    @Test
    internal fun testJsonSerialization() {
        val user = User.createClient("fulano", "fulano @gmail.com", "1234")

        val serialized = Json.encodeToString(user)

        println(serialized)

        val decoded = Json.decodeFromString<User>(serialized)

        assert(decoded.salt == null)
    }
}