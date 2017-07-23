package comexpensetracker.medium.extra_expensetracker

import com.google.gson.annotations.SerializedName

/**
 * Created by akash on 23/7/17.
 */
class AuthRequest {

    @SerializedName("username")
    var username: String = ""

    @SerializedName("password")
    var password: String = ""

    @SerializedName("email")
    var email: String? = null

    @SerializedName("mobile")
    var mobile: String? = null

    fun AuthRequest(username: String, password: String, email: String, mobile: String) {
        this.username = username
        this.password = password
        this.email = email
        this.mobile = mobile
    }

}
