package comexpensetracker.medium.extra_expensetracker;

import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 4/7/17.
 */

public class AuthRequest {

    @SerializedName("username")
    String username;

    @SerializedName("password")
    String password;

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
