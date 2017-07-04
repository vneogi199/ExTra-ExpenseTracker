package comexpensetracker.medium.extra_expensetracker;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by akash on 4/7/17.
 */

public interface HasuraAuthInterface {

    @POST(Endpoint.LOGIN)
    Call<AuthResponse> login(@Body AuthRequest request);

    @POST(Endpoint.REGISTER)
    Call<AuthResponse> register(@Body AuthRequest request);

    @GET(Endpoint.LOGOUT)
    Call<MessageResponse> logout();

}
