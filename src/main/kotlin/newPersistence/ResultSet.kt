package newPersistence

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ResultSet<Type>(
    val rows:List<Type>,
    @SerialName("current_page")
    val currentPage:Int,
    @SerialName("number_of_pages")
    val numberOfPages:Int,
    @SerialName("page_length")
    val pageLength:Int
) {
}