package comexpensetracker.medium.extra_expensetracker;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by akash on 15/7/17.
 */

public interface HasuraDbInterface {

    @POST(Endpoint.QUERY)
    Call<List<AddexpenseRecord>> getExpense(@Body SelectExpenseQuery query);

    @POST(Endpoint.QUERY)
    Call<AddExpenseReturingResponse> addExpense(@Body AddExpenseQuery query);

    @POST(Endpoint.QUERY)
    Call<AddExpenseReturingResponse> deleteExpense(@Body DeleteExpenseQuery query);

    @POST(Endpoint.QUERY)
    Call<AddExpenseReturingResponse> updateExpense(@Body UpdateExpenseQuery query);

}
