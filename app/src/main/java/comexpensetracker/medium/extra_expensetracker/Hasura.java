package comexpensetracker.medium.extra_expensetracker;

import android.content.Context;
import android.content.SharedPreferences;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by akash on 4/7/17.
 */

public class Hasura {

    static String PREFS_NAME = "PrefsName";
    static String USERID = "userId";
    static String ROLE = "role";
    static String SESSIONID = "sessionId";

    public static HasuraAuthInterface auth;

    private static SharedPreferences sharedPreferences;

    public static void initialise(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Endpoint.AUTH_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        auth = retrofit.create(HasuraAuthInterface.class);
    }

    public static void setSession(int userId, String sessionId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SESSIONID,sessionId);
        editor.putInt(USERID,userId);
        editor.apply();
    }

    public static void setSession(AuthResponse response) {
        setSession(response.getId(), response.getAuthToken());
    }

    public static int getUserId() {
        return sharedPreferences.getInt(USERID,-1);
    }

    public static String getUserSessionId() {
        return sharedPreferences.getString(SESSIONID,null);
    }

    public static void clearSession() {
        setSession(-1,null);
    }

}
