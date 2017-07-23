package comexpensetracker.medium.extra_expensetracker

import com.google.gson.annotations.SerializedName



/**
 * Created by akash on 23/7/17.
 */
class AuthResponse {

    @SerializedName("auth_token")
    var authToken: String? = null

    @SerializedName("hasura_id")
    var id: Int? = null

    @SerializedName("hasura_roles")
    var roles: List<String>? = null

    fun getAuthToken(): String? {
        return authToken
    }

    fun getId(): Int? {
        return id
    }

    fun getRoles(): List<String>? {
        return roles
    }

}
