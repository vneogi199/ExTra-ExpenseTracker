package comexpensetracker.medium.extra_expensetracker


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.Adapter
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
import java.sql.Timestamp
import java.util.*


class ViewExpense : AppCompatActivity() {
    val client = Hasura.getClient()!!
    var user : HasuraUser = Hasura.getClient().user
    var mRecyclerView:RecyclerView? = null
    var layoutManager:RecyclerView.LayoutManager? = null
    var mRecyclerViewItems: List<Any> = ArrayList()

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
        try {
//            val jsonObject = JSONObject("  {\"type\":\"select\"," +
//                    " \"args\":{" +
//                    "\"table\":\"expense\", \"columns"\: ["id", "title"],
//                    "}" +
//                    "}")

            var columnsArray = JSONArray()
            columnsArray.put("exp_name")
            columnsArray.put("exp_amt")
            columnsArray.put("exp_created")
            columnsArray.put("exp_category")

            var args = JSONObject()
            args.put("table", "expense")
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
                            var JSONresponse:String = ""
                                for (record in response) {
                                    JSONresponse += record.toString()
                                }
                            displayExpenses(JSONresponse)
                            //adapter.setData(response)
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

    fun displayExpenses(JSONresponse:String) {

        mRecyclerView = findViewById(R.id.list_of_expenses) as RecyclerView?
        mRecyclerView?.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(this)
        mRecyclerView?.layoutManager = layoutManager

        var adapter: Adapter<RecyclerView.ViewHolder> = RecyclerViewAdapter(this, mRecyclerViewItems)
        mRecyclerView?.adapter = adapter

        addMenuItemsFromJson(JSONresponse)
    }

    fun addMenuItemsFromJson(JSONresponse:String) {
        try {
            var jsonDataString:String = JSONresponse
            var expenseItemsJsonArray:JSONArray = JSONArray(jsonDataString)


        for (i in 0..expenseItemsJsonArray.length()-1){

                var expenseItemObject = expenseItemsJsonArray.getJSONObject(i)
                var expensename:String = expenseItemObject.getString("exp_name")
                var expenseamt:Int = expenseItemObject.getInt("exp_amt")
                var expensecategory:Int = expenseItemObject.getInt("exp_category")
                var expensecreated: String = expenseItemObject.getString("exp_created")

                var expenseRecord:ExpenseRecord = ExpenseRecord(expensename, expenseamt, expensecreated, expensecategory)

                mRecyclerViewItems+=expenseRecord
            }
        } catch (e:Exception) {
            Toast.makeText(this@ViewExpense, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}
