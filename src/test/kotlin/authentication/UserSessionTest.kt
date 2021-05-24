package authentication

import authentication.exception.UserDeactivatedException
import authentication.exception.UserOfflineException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class UserSessionTest {
    @Test
    internal fun testCreateSession() {
        val now = LocalDateTime.now()
        val user = User.createClient("fulano", "fulano @gmail.com", "11122233344", null,"1234")
        val session = UserSession(user, now)

        assert(session.start == now)
        assertFalse(session.hasEnded())
        assert(session.end == null)
    }

    @Test
    internal fun testTerminateSession() {
        val start = LocalDateTime.now()
        val user = User.createClient("fulano", "fulano @gmail.com", "11122233344", null,"1234")
        val session = UserSession(user, start)

        assertFalse(session.hasEnded())
        assertDoesNotThrow(fun(){ session.endSession(LocalDateTime.now())})
        assert(session.hasEnded())
        assert(user.status == UserStatus.Offline)
        assertThrows(UserOfflineException::class.java, fun(){ session.endSession(LocalDateTime.now()) })

        user.deactivateAccount()

        assertThrows(UserDeactivatedException::class.java, fun(){ UserSession(user, start) })

    }
}