package comexpensetracker.medium.extra_expensetracker;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenseMainActivity extends BaseActivity {

    AddExpenseRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    public static void startActivity(Activity startingActivity) {
        startingActivity.startActivity(new Intent(startingActivity,ExpenseMainActivity.class));
        startingActivity.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_main);

        //recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new AddExpenseRecyclerViewAdapter(new AddExpenseRecyclerViewAdapter.Interactor() {
            @Override
            public void onExpenseClicked(int position, AddexpenseRecord record) {
                toggleExpense(position, record);
            }

            @Override
            public void onExpenseLongClicked(int position, AddexpenseRecord record) {
                deleteExpense(position, record);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        fetchExpensesFromDB();

    }

    private void handleError(ResponseBody error) {
        try {
            MessageResponse messageResponse = new Gson().fromJson(error.string(), MessageResponse.class);
            showErrorAlert(messageResponse.getMessage(),null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fetchExpensesFromDB() {
        showProgressIndicator();
        Hasura.db.getExpense(new SelectExpenseQuery(Hasura.getUserId())).enqueue(new Callback<List<AddexpenseRecord>>() {
            @Override
            public void onResponse(Call<List<AddexpenseRecord>> call, Response<List<AddexpenseRecord>> response) {
                hideProgressIndicator();
                if (response.isSuccessful()) {
                    adapter.setData(response.body());
                } else {
                    handleError(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<AddexpenseRecord>> call, Throwable t) {
                hideProgressIndicator();
                showErrorAlert("Something went wrong. Please ensure that you have a working internet connection",null);
            }
        });
    }

    private void toggleExpense(final int recyclerViewPostion, final AddexpenseRecord record) {
        showProgressIndicator();
        UpdateExpenseQuery query = new UpdateExpenseQuery(record.getExpId(), Hasura.getUserId(), record.getExpName(), record.getExpAmt(), record.getExpCreated(), record.getExpNote(), record.getExpTag(), record.getExpCategory());
        Hasura.db.updateExpense(query).enqueue(new Callback<AddExpenseReturingResponse>() {
            @Override
            public void onResponse(Call<AddExpenseReturingResponse> call, Response<AddExpenseReturingResponse> response) {
                hideProgressIndicator();
                if (response.isSuccessful()) {
                    adapter.updateData(recyclerViewPostion,record);
                } else {
                    handleError(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<AddExpenseReturingResponse> call, Throwable t) {
                hideProgressIndicator();
                showErrorAlert("Something went wrong. Please ensure that you have a working internet connection",null);
            }
        });

    }

    private void deleteExpense(final int recyclerViewPosition, final AddexpenseRecord record) {
        showProgressIndicator();
        Hasura.db.deleteExpense(new DeleteExpenseQuery(record.getExpId(),Hasura.getUserId())).enqueue(new Callback<AddExpenseReturingResponse>() {
            @Override
            public void onResponse(Call<AddExpenseReturingResponse> call, Response<AddExpenseReturingResponse> response) {
                hideProgressIndicator();
                if (response.isSuccessful()) {
                    adapter.deleteData(recyclerViewPosition,record);
                } else {
                    handleError(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<AddExpenseReturingResponse> call, Throwable t) {
                hideProgressIndicator();
                showErrorAlert("Please ensure that you have a working internet connection",null);
            }
        });
    }

    private void addExpense(final String description) {
        showProgressIndicator();
        Hasura.db.addExpense(new AddExpenseQuery(description,Hasura.getUserId())).enqueue(new Callback<AddExpenseReturingResponse>() {
            @Override
            public void onResponse(Call<AddExpenseReturingResponse> call, Response<AddExpenseReturingResponse> response) {
                hideProgressIndicator();
                if (response.isSuccessful()) {
                    AddexpenseRecord record = new AddexpenseRecord(description,Hasura.getUserId(), 500);
                    record.setExpId(response.body().getAddexpenseRecords().get(0).getExpId());
                    adapter.addData(record);
                } else {
                    handleError(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<AddExpenseReturingResponse> call, Throwable t) {
                hideProgressIndicator();
                showErrorAlert("Please ensure that you have a working internet connection",null);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addTodo:
                final AlertDialog.Builder alert = new AlertDialog.Builder(this);
                final EditText edittext = new EditText(this);
                alert.setMessage("Describe your task");
                alert.setTitle("Create new task");
                alert.setView(edittext);
                alert.setPositiveButton("Add Todo", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        addExpense(edittext.getText().toString());
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.show();
                return true;
            case R.id.signOut:
                AlertDialog.Builder signOutAlert = new AlertDialog.Builder(this);
                signOutAlert.setTitle("Sign Out");
                signOutAlert.setMessage("Are you sure you want to sign out?");
                signOutAlert.setNeutralButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                signOutAlert.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showProgressIndicator();
                        Hasura.auth.logout().enqueue(new Callback<MessageResponse>() {
                            @Override
                            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                                hideProgressIndicator();
                                if (response.isSuccessful()) {
                                    completeUserLogout();
                                } else {
                                    handleError(response.errorBody());
                                }
                            }

                            @Override
                            public void onFailure(Call<MessageResponse> call, Throwable t) {
                                hideProgressIndicator();
                                showErrorAlert("Please ensure that you have a working internet connection",null);
                            }
                        });
                    }
                });
                signOutAlert.show();
                return true;
        }
        return false;
    }

    private void completeUserLogout() {
        Hasura.clearSession();
        RegisterForm.startActivity(ExpenseMainActivity.this);
    }

}
