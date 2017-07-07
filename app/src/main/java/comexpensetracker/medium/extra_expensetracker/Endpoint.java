package comexpensetracker.medium.extra_expensetracker;

/**
 * Created by akash on 4/7/17.
 */

public class Endpoint {
    public static final String AUTH_URL = "http://auth.expensetrackerhasura.hasura.me/";
    public static final String DB_URL = "http://data.expensetrackerhasura.hasura.me/";
    public static final String VERSION = "v1";

    public static final String LOGIN = "login";
    public static final String REGISTER = "signup";
    public static final String LOGOUT = "user/logout";
    public static final String QUERY = Endpoint.VERSION + "/query";
}
