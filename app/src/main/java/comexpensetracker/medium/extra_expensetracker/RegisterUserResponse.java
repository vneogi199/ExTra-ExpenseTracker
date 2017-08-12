package comexpensetracker.medium.extra_expensetracker;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vinit on 26/7/17.
 */

public class RegisterUserResponse {

    @SerializedName("user_id")
    Integer user_id;

    @SerializedName("name")
    Integer name;

    public RegisterUserResponse(Integer name) {
        this.name = name;
    }
}
