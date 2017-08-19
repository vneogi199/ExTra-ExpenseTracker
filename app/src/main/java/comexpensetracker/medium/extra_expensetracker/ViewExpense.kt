package comexpensetracker.medium.extra_expensetracker


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
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
import io.hasura.sdk.responseListener.LogoutResponseListener
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class ViewExpense : AppCompatActivity() {
    val client = Hasura.getClient()!!
    var user : HasuraUser = Hasura.getClient().user
    var adapter: ExpenseRecyclerViewAdapter? = ExpenseRecyclerViewAdapter()
    var recyclerView: RecyclerView? = null
    val layoutManager = LinearLayoutManager(this)
    private var fabExpanded : Boolean = false

    private var fabNext : FloatingActionButton? = null

    private var layoutFabAddExpense : LinearLayout? = null
    private var layoutFabViewAnalytics : LinearLayout?= null
    private var layoutLogout : LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expense)

        fabNext = findViewById(R.id.nextBtn) as FloatingActionButton?
        layoutFabAddExpense = findViewById(R.id.layoutAddExpense) as LinearLayout?
        layoutFabViewAnalytics = findViewById(R.id.layoutViewAnalytics) as LinearLayout?
        layoutLogout = findViewById(R.id.layoutLogout) as LinearLayout?

        try {
            Hasura.setProjectConfig(ProjectConfig.Builder()
                    .setCustomBaseDomain("cobalt21.hasura-app.io")
                    //.enableOverHttp()
                    .build())
                    .initialise(this)
        }   catch (e: HasuraInitException) {
                e.printStackTrace()
        }

        fetchExpensesFromDB()

        fabNext?.setOnClickListener{
            if(fabExpanded) closeSubMenusFab()
            else openSubMenusFab()
        }
        closeSubMenusFab()
    }

    private fun fetchExpensesFromDB() {
        try {
//            val jsonObject = JSONObject("  {\"type\":\"select\"," +
//                    " \"args\":{" +
//                    "\"table\":\"expense_info_view\", \"columns"\: ["id", "title"],
//                    "}" +
//                    "}")

            val columnsArray = JSONArray()
            columnsArray.put("exp_name")
            columnsArray.put("exp_amt")
            columnsArray.put("exp_created")
            columnsArray.put("category_name")

            val args = JSONObject()
            args.put("table", "expense_info_view")
            args.put("columns", columnsArray)

            val userIDQuery = JSONObject()
            userIDQuery.put("user_id", user.id)

            args.put("where", userIDQuery)
            args.put("order_by", "exp_created")

            val selectExpenseQuery = JSONObject()
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
                            recyclerView?.addItemDecoration(DividerItemDecoration(this@ViewExpense,DividerItemDecoration.VERTICAL))
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
        layoutLogout?.setOnClickListener{
            val signOutAlert = AlertDialog.Builder(this)
            signOutAlert.setTitle("Sign Out")
            signOutAlert.setMessage("Are you sure you want to sign out?")
            signOutAlert.setNeutralButton("No", { _, _ -> })
            signOutAlert.setNegativeButton("Yes", { _, _ ->
                user.logout(object : LogoutResponseListener {
                    override fun onSuccess(message: String) {
                        Toast.makeText(this@ViewExpense, "Logged out successfully", Toast.LENGTH_SHORT).show()
                        val i = Intent(this@ViewExpense, RegisterForm::class.java)
                        startActivity(i)
                    }

                    override fun onFailure(e: HasuraException) {
                        Toast.makeText(this@ViewExpense, "Couldn't logout", Toast.LENGTH_SHORT).show()
                    }
                })
            })
            signOutAlert.show()
        }
    }

    private fun closeSubMenusFab() {

        layoutFabAddExpense?.visibility = View.INVISIBLE
        layoutFabViewAnalytics?.visibility = View.INVISIBLE
        layoutLogout?.visibility = View.INVISIBLE
        fabNext?.setImageResource(R.drawable.ic_navigate_next_black_24dp)
        fabExpanded = false
    }

    //Opens FAB submenus
    private fun openSubMenusFab() {
        layoutFabAddExpense?.visibility = View.VISIBLE
        layoutFabViewAnalytics?.visibility = View.VISIBLE
        layoutLogout?.visibility = View.VISIBLE
        //Change settings icon to 'X' icon
        fabNext?.setImageResource(R.drawable.ic_close_black_24dp)
        fabExpanded = true
    }

}
