package document.civilRegistry

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Serializable
class Registering (
    var id:String?,
    @SerialName("document_id")
    val documentID:String,
    val text:String
){
    fun createId(): String {
        val md = MessageDigest.getInstance("SHA")
        val now = LocalDateTime.now(ZoneOffset.UTC)
        return Base64.getUrlEncoder().encodeToString(md.digest(now.toString().toByteArray()))
    }

    override fun equals(other: Any?): Boolean {
        if (other?.javaClass != javaClass) return false
        other as Registering
        if (other.id !== id || other.documentID !== documentID || other.text !== text) return false
        return true
    }
}
