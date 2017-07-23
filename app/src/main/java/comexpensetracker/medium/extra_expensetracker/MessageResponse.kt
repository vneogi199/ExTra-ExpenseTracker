package comexpensetracker.medium.extra_expensetracker

import com.google.gson.annotations.SerializedName



/**
 * Created by akash on 23/7/17.
 */
class MessageResponse {

    @SerializedName("message")
    var message: String? = null

    fun getMessage(): String? {
        return message
    }

}
