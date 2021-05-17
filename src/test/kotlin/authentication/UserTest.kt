package authentication

import authentication.exception.InvalidPasswordException
import authentication.exception.UserDeactivatedException
import authentication.exception.UserOfflineException
import authentication.exception.UserOnlineException
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

        user.addPermission(Permission(user.id, Subject.Blockchain, null))
        user.addPermission(Permission(user.id, Subject.Blockchain, null))

        assert(user.permissions.size == defaultPermissionLength + 1)

        user.removePermission(Permission(user.id, Subject.Blockchain, null))

        assert(user.permissions.size == defaultPermissionLength)

        user.addPermission(Permission(user.id, Subject.Blockchain, null))

        assertNotNull(user.getPermission(Subject.Blockchain, null))
    }

    @Test
    internal fun testPermissionCheck() {
        val client = User.createClient("fulano", "fulano @gmail.com", "1234")

        assert(client.isAuthorized(null, null))
        assert(client.isAuthorized(Role.Client, null))
        assert(client.isAuthorized(Role.Client, Permission.getClientDefaultPermissions(client.id!!).first()))

        assertFalse(client.isAuthorized(Role.SysAdmin, null))
        assertFalse(client.isAuthorized(Role.SysAdmin, Permission(client.id, Subject.Blockchain, null)))
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

    @Test
    internal fun testStatus() {
        val user = User.createClient("fulano", "fulano@gmail.com", "1234")

        assert(user.status == UserStatus.Offline)
        assertThrows(UserOfflineException::class.java, fun(){ user.logout() })
        assertDoesNotThrow(fun(){user.login()})

        assert(user.status == UserStatus.Online)
        assertThrows(UserOnlineException::class.java, fun(){ user.login() })
        assertDoesNotThrow(fun(){ user.logout() })

        assert(user.status == UserStatus.Offline)
        assertThrows(UserOfflineException::class.java, fun(){ user.reactivateAccount() })
        assertDoesNotThrow(fun(){ user.deactivateAccount() })

        assert(user.status == UserStatus.Deactivated)
        assertThrows(UserDeactivatedException::class.java, fun(){ user.login() })
        assertThrows(UserDeactivatedException::class.java, fun(){ user.logout() })
        assertThrows(UserDeactivatedException::class.java, fun(){ user.deactivateAccount() })
        assertDoesNotThrow(fun(){ user.reactivateAccount() })
        assert(user.status == UserStatus.Offline)
    }
}