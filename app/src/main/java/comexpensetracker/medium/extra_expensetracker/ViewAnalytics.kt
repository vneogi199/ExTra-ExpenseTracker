package comexpensetracker.medium.extra_expensetracker

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.github.mikephil.charting.charts.PieChart
import io.hasura.sdk.*
import io.hasura.sdk.exception.HasuraException
import io.hasura.sdk.exception.HasuraInitException
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by vinit on 10/8/17.
 */
class ViewAnalytics : AppCompatActivity() {
    var user: HasuraUser? = null
    var client: HasuraClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_graph)
        try {
            Hasura.setProjectConfig(ProjectConfig.Builder()
                    .setCustomBaseDomain("cobalt21.hasura-app.io")
                    //.enableOverHttp()  //enable this
                    .build())
                    .initialise(this)
        } catch (e: HasuraInitException) {
            e.printStackTrace()
        }

        client = Hasura.getClient()
        user = client?.user

        showGraph()


    }

    fun showGraph() {
        var pieChart: PieChart = findViewById(R.id.piechart) as PieChart
        pieChart.setUsePercentValues(true)
        var expenseArray = IntArray(9)
        try {
//            val jsonObject = JSONObject("  {\"type\":\"select\"," +
//                    " \"args\":{" +
//                    "\"table\":\"expense_info_view\", \"columns"\: ["id", "title"],
//                    "}" +
//                    "}")

            var columnsArray = JSONArray()
            columnsArray.put("exp_amt")
            columnsArray.put("category_name")

            var args = JSONObject()
            args.put("table", "expense_info_view")
            args.put("columns", columnsArray)

            var userIDQuery = JSONObject()
            userIDQuery.put("user_id", user?.id)

            args.put("where", userIDQuery)

            var selectExpenseQuery = JSONObject()
            selectExpenseQuery.put("type", "select")
            selectExpenseQuery.put("args", args)
            Log.i("ResponseRecord", selectExpenseQuery.toString())
            client?.useDataService()?.setRequestBody(selectExpenseQuery)
                    ?.expectResponseTypeArrayOf(ExpenseRecord::class.java)
                    ?.enqueue(object : Callback<List<ExpenseRecord>, HasuraException> {
                        override fun onSuccess(response: List<ExpenseRecord>) {
                            for (record in response) {
                                Log.i("ResponseRecord", record.getExpCategory().toString())
                                when(record.getExpCategory()){
                                    "Bills" -> expenseArray[0] = expenseArray[0] + record.getExpAmt()
                                    "Groceries" -> expenseArray[1] = expenseArray[1] + record.getExpAmt()
                                    "Entertainment" -> expenseArray[2] = expenseArray.get(2) + record.getExpAmt()
                                    "Fuel" -> expenseArray[3] = expenseArray[3] + record.getExpAmt()
                                    "Food" -> expenseArray[4] = expenseArray[4] + record.getExpAmt()
                                    "Health" -> expenseArray[5] = expenseArray[5] + record.getExpAmt()
                                    "Travel" -> expenseArray[6] = expenseArray[6] + record.getExpAmt()
                                    "Shopping" -> expenseArray[7] = expenseArray[7] + record.getExpAmt()
                                    "Other" -> expenseArray[8] = expenseArray[8] + record.getExpAmt()
                                    else -> Toast.makeText(this@ViewAnalytics, "Error while calculating", Toast.LENGTH_SHORT).show()
                                }
                            }
                            for(element in expenseArray){
                                Log.d("ExpenseArray", element.toString())
                            }
                        }

                        override fun onFailure(e: HasuraException) {
//                            hideProgressIndicator()
//                            handleError(e)
                            Toast.makeText(this@ViewAnalytics, e.toString(), Toast.LENGTH_SHORT).show()
                        }
                    })

        } catch (e: JSONException) {
            Toast.makeText(this@ViewAnalytics, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

}