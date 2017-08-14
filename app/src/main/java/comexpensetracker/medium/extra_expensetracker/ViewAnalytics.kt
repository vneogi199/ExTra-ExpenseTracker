package comexpensetracker.medium.extra_expensetracker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import io.hasura.sdk.*
import io.hasura.sdk.exception.HasuraException
import io.hasura.sdk.exception.HasuraInitException
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import android.widget.ArrayAdapter




/**
 * Created by vinit on 10/8/17.
 */
class ViewAnalytics : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    var spinner : Spinner? = null
    var options = arrayOf("Graph", "Pie Chart")
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

        spinner = findViewById(R.id.spinner) as Spinner?
        val adapter = ArrayAdapter<String>(this@ViewAnalytics,
                R.layout.activity_view_expense, options)
        //adapter.setDropDownViewResource()
        //TODO()

        showGraph()
    }

    fun showGraph() {
        var pieChart: PieChart = findViewById(R.id.piechart) as PieChart
        pieChart.setUsePercentValues(true)
        var totalArray = IntArray(9)
        for(i in 0..8) totalArray[i] = 0
        var expenseArray : ArrayList<Entry> = ArrayList()
        var categoryValues : ArrayList<String> = ArrayList()
        categoryValues.add("Bills")
        categoryValues.add("Groceries")
        categoryValues.add("Entertainment")
        categoryValues.add("Fuel")
        categoryValues.add("Food")
        categoryValues.add("Health")
        categoryValues.add("Travel")
        categoryValues.add("Shopping")
        categoryValues.add("Other")

        var xVals : ArrayList<String> = ArrayList()
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
                                    "Bills" -> totalArray[0] = totalArray[0] + record.getExpAmt()
                                    "Groceries" -> totalArray[1] = totalArray[1] + record.getExpAmt()
                                    "Entertainment" -> totalArray[2] = totalArray[2] + record.getExpAmt()
                                    "Fuel" -> totalArray[3] = totalArray[3] + record.getExpAmt()
                                    "Food" -> totalArray[4] = totalArray[4] + record.getExpAmt()
                                    "Health" -> totalArray[5] = totalArray[5] + record.getExpAmt()
                                    "Travel" -> totalArray[6] = totalArray[6] + record.getExpAmt()
                                    "Shopping" -> totalArray[7] = totalArray[7] + record.getExpAmt()
                                    "Other" -> totalArray[8] = totalArray[8] + record.getExpAmt()
                                    else -> Toast.makeText(this@ViewAnalytics, "Error while calculating", Toast.LENGTH_SHORT).show()
                                }
                            }
                            for(i in 0..8){
                                if(totalArray[i] != 0){
                                    expenseArray.add(Entry(totalArray[i].toFloat(), i))
                                    xVals.add(categoryValues[i])
                                }

                            }

                            var data_set:PieDataSet = PieDataSet(expenseArray, "")
                            var data:PieData = PieData(xVals, data_set)
                            data.setValueFormatter(PercentFormatter())
                            pieChart.data = data
                            pieChart.setDescription("")
                            pieChart.isDrawHoleEnabled = false
                            data_set.setColors(ColorTemplate.JOYFUL_COLORS)
                            var legend:Legend = pieChart.legend
                            legend.isEnabled = false
                            data.setValueTextSize(15f)

                            Log.d("EXPENSE ARRAY", expenseArray.toString())
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
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}