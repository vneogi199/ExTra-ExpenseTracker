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

    @SerializedName("email")
    String email;

    @SerializedName("mobile")
    String mobile;

    public AuthRequest(String username, String password, String email, String mobile) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
    }
    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
