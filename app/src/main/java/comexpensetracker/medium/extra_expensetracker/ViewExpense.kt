package comexpensetracker.medium.extra_expensetracker


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import io.hasura.sdk.Callback
import io.hasura.sdk.Hasura
import io.hasura.sdk.HasuraUser
import io.hasura.sdk.ProjectConfig
import io.hasura.sdk.exception.HasuraInitException
import io.hasura.sdk.exception.HasuraException
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class ViewExpense : AppCompatActivity() {
    val client = Hasura.getClient()!!
    var user : HasuraUser = Hasura.getClient().user
    var adapter: ExpenseRecyclerViewAdapter? = null
    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expense)

        recyclerView = findViewById(R.id.list_of_expenses) as RecyclerView?
        val layoutManager = LinearLayoutManager(this)

        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter

        try {
            Hasura.setProjectConfig(ProjectConfig.Builder()
                    .setCustomBaseDomain("cobalt21.hasura-app.io")
                    //.enableOverHttp()
                    .build())
                    .initialise(this)
        }   catch (e: HasuraInitException) {
                e.printStackTrace()
        }

//        val drawable2 = TextDrawable.builder()
//                .buildRound("A", Color.RED)
//        val image: ImageView = findViewById(R.id.image_view) as ImageView
//        image.setImageDrawable(drawable2)
        fetchExpensesFromDB()
    }

    private fun fetchExpensesFromDB() {
        try {
//            val jsonObject = JSONObject("  {\"type\":\"select\"," +
//                    " \"args\":{" +
//                    "\"table\":\"expense_info_view\", \"columns"\: ["id", "title"],
//                    "}" +
//                    "}")

            var columnsArray = JSONArray()
            columnsArray.put("exp_name")
            columnsArray.put("exp_amt")
            columnsArray.put("exp_created")
            columnsArray.put("category_name")

            var args = JSONObject()
            args.put("table", "expense_info_view")
            args.put("columns", columnsArray)

            var userIDQuery = JSONObject()
            userIDQuery.put("user_id", user.id)

            args.put("where", userIDQuery)
            args.put("order_by", "exp_created")

            var selectExpenseQuery = JSONObject()
            selectExpenseQuery.put("type", "select")
            selectExpenseQuery.put("args", args)
            Log.i("ResponseRecord", selectExpenseQuery.toString())
            client.useDataService()
                    .setRequestBody(selectExpenseQuery)
                    .expectResponseTypeArrayOf(ExpenseRecord::class.java)
                    .enqueue(object : Callback<List<ExpenseRecord>, HasuraException> {
                        override fun onSuccess(response: List<ExpenseRecord>) {
                                for (record in response) {
                                    Log.i("ResponseRecord", record.toString())
                                }
                            adapter?.setExpense(response)
                        }

                        override fun onFailure(e: HasuraException) {
//                            hideProgressIndicator()
//                            handleError(e)
                            Toast.makeText(this@ViewExpense, e.toString(), Toast.LENGTH_SHORT).show()
                        }
                    })

        } catch (e: JSONException) {
            Toast.makeText(this@ViewExpense, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}
