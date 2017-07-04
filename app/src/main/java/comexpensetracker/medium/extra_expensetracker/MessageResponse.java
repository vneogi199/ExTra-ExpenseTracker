package comexpensetracker.medium.extra_expensetracker;

import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 4/7/17.
 */

public class MessageResponse {

    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }

}
