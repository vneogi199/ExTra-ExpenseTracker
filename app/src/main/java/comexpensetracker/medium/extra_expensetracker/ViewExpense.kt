package comexpensetracker.medium.extra_expensetracker


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import io.hasura.sdk.Callback
import io.hasura.sdk.Hasura
import io.hasura.sdk.HasuraUser
import io.hasura.sdk.ProjectConfig
import io.hasura.sdk.exception.HasuraInitException
import io.hasura.sdk.exception.HasuraException
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject




class ViewExpense : AppCompatActivity() {
    val client = Hasura.getClient()!!
    var user : HasuraUser = Hasura.getClient().user
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expense)
        var list_of_expensesLayout : RecyclerView = findViewById(R.id.list_of_expenses) as RecyclerView
        list_of_expensesLayout.setHasFixedSize(true)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        list_of_expensesLayout.layoutManager = llm

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
        //test for JSONObject
        try {
            val jsonObject = JSONObject("  {\"type\":\"select\"," +
                    " \"args\":{" +
                    "\"table\":\"expense\", \"columns"\: ["id", "title"],
                    "}" +
                    "}")

            var columnsArray = listOf<String>("exp_name", "exp_amt", "exp_created", "exp_category")

            var args = JSONObject()
            args.put("table", "expense")
            args.put("columns", columnsArray)

            var userIDQuery = JSONObject()
            userIDQuery.put("user_id", user.id)

            args.put("where", userIDQuery)

            var selectExpenseQuery = JSONObject()
            selectExpenseQuery.put("type", "select")
            selectExpenseQuery.put("args", args)



            client.useDataService()
                    .setRequestBody(jsonObject)
                    .expectResponseTypeArrayOf(TodoRecord::class.java)
                    .enqueue(object : Callback<List<TodoRecord>, HasuraException>() {
                        override fun onSuccess(response: List<TodoRecord>) {

                            for (record in response) {
                                Log.i("ResponseRecord", record.toString())
                            }
                            hideProgressIndicator()
                            adapter.setData(response)
                        }

                        override fun onFailure(e: HasuraException) {
                            hideProgressIndicator()
                            handleError(e)
                        }
                    })

        } catch (e: JSONException) {

        }


        client.useDataService()
                .setRequestBody(SelectTodoRequest(user.id))
                .expectResponseTypeArrayOf(TodoRecord::class.java)
                .enqueue(object : Callback<List<TodoRecord>, HasuraException>() {
                    fun onSuccess(response: List<TodoRecord>) {

                        for (record in response) {
                            Log.i("ResponseRecord", record.toString())
                        }
                        hideProgressIndicator()
                        adapter.setData(response)
                    }

                    fun onFailure(e: HasuraException) {
                        hideProgressIndicator()
                        handleError(e)
                    }
                })
    }
}
