package comexpensetracker.medium.extra_expensetracker


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import io.hasura.sdk.Callback
import io.hasura.sdk.Hasura
import io.hasura.sdk.HasuraUser
import io.hasura.sdk.ProjectConfig
import io.hasura.sdk.exception.HasuraException
import io.hasura.sdk.exception.HasuraInitException
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class ViewExpense : AppCompatActivity() {
    val client = Hasura.getClient()!!
    var user : HasuraUser = Hasura.getClient().user
    var adapter: ExpenseRecyclerViewAdapter? = ExpenseRecyclerViewAdapter()
    var recyclerView: RecyclerView? = null
    val layoutManager = LinearLayoutManager(this)
    var fabExpanded : Boolean = false

    var fabNext : FloatingActionButton? = null

    var layoutFabAddExpense : LinearLayout? = null
    var layoutFabViewAnalytics : LinearLayout?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expense)

        fabNext = findViewById(R.id.nextBtn) as FloatingActionButton?
        layoutFabAddExpense = findViewById(R.id.layoutAddExpense) as LinearLayout?
        layoutFabViewAnalytics = findViewById(R.id.layoutViewAnalytics) as LinearLayout?


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

        fabNext?.setOnClickListener{
            if(fabExpanded) closeSubMenusFab()
            else openSubMenusFab()
        }
        closeSubMenusFab()
    }

    fun fetchExpensesFromDB() {
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
                            recyclerView = findViewById(R.id.list_of_expenses) as RecyclerView?
                            layoutManager.orientation = LinearLayoutManager.VERTICAL
                            recyclerView?.layoutManager = layoutManager
                            recyclerView?.adapter = adapter
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
        layoutFabAddExpense?.setOnClickListener{
            val addExpenseIntent = Intent(this@ViewExpense, AddExpense::class.java)
            startActivity(addExpenseIntent)
        }
        layoutFabViewAnalytics?.setOnClickListener{
            val viewAnalyticsIntent = Intent(this@ViewExpense, ViewAnalytics::class.java)
            startActivity(viewAnalyticsIntent)
        }
    }

    private fun closeSubMenusFab() {

        layoutFabAddExpense?.visibility = View.INVISIBLE
        layoutFabViewAnalytics?.visibility = View.INVISIBLE
        fabNext?.setImageResource(R.drawable.ic_navigate_next_black_24dp)
        fabExpanded = false
    }

    //Opens FAB submenus
    private fun openSubMenusFab() {
        layoutFabAddExpense?.visibility = View.VISIBLE
        layoutFabViewAnalytics?.visibility = View.VISIBLE
        //Change settings icon to 'X' icon
        fabNext?.setImageResource(R.drawable.ic_close_black_24dp)
        fabExpanded = true
    }
}
