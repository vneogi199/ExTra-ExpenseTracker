package comexpensetracker.medium.extra_expensetracker;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by akash on 15/7/17.
 */

public class HasuraTokenInterceptor implements Interceptor {
    @Override
    public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Response response;
        String session = Hasura.getUserSessionId();
        if (session == null) {
            response = chain.proceed(request);
        } else {
            Request newRequest = request.newBuilder()
                    .addHeader("Authorization", "Bearer " + session)
                    .build();
            response = chain.proceed(newRequest);
        }
        return response;
    }
}
