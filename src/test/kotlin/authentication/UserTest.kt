package authentication

import authentication.exception.InvalidPasswordException
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

    @Test
    internal fun testPermissions() {
        val user = User.createClient("fulano", "fulano @gmail.com", "1234")
        val defaultPermissionLength = user.permissions.size

        user.addPermission(Permission(Subject.Blockchain, null))
        user.addPermission(Permission(Subject.Blockchain, null))

        assert(user.permissions.size == defaultPermissionLength + 1)

        user.removePermission(Permission(Subject.Blockchain, null))

        assert(user.permissions.size == defaultPermissionLength)

        user.addPermission(Permission(Subject.Blockchain, null))

        assertNotNull(user.getPermission(Subject.Blockchain, null))
    }

    @Test
    internal fun testPassword() {
        val user = User.createClient("fulano", "fulano@gmail.com", "1234")

        assert(user.validatePassword("1234"))
        assert(!user.validatePassword(""))

        user.changePassword("1234", "4444")

        assert(user.validatePassword("4444"))

        assertThrows(InvalidPasswordException::class.java, fun() { user.changePassword("1111", "1111") })
    }
}