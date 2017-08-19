package comexpensetracker.medium.extra_expensetracker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import io.hasura.sdk.*
import io.hasura.sdk.exception.HasuraException
import io.hasura.sdk.exception.HasuraInitException
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


/**
 * Created by vinit on 10/8/17.
 */
class ViewAnalytics : AppCompatActivity(), AdapterView.OnItemSelectedListener {


    var spinner : Spinner? = null
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
        val adapter = ArrayAdapter.createFromResource(this, R.array.dropdown_list, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = adapter
        spinner?.onItemSelectedListener = this
    }

    private fun showPieChart() {
        var pieChart: PieChart = findViewById(R.id.piechart) as PieChart
        pieChart.visibility = View.VISIBLE
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

                            var data_set = PieDataSet(expenseArray, "")
                            var data = PieData(xVals, data_set)
                            data.setValueFormatter(PercentFormatter())
                            pieChart.data = data
                            pieChart.setDescription("")
                            pieChart.isDrawHoleEnabled = false
                            data_set.setColors(ColorTemplate.JOYFUL_COLORS)
                            var legend:Legend = pieChart.legend
                            legend.isEnabled = false
                            data.setValueTextSize(15f)
                            pieChart.invalidate()
                        }

                        override fun onFailure(e: HasuraException) {
                            Toast.makeText(this@ViewAnalytics, e.toString(), Toast.LENGTH_SHORT).show()
                        }
                    })

        } catch (e: JSONException) {
            Toast.makeText(this@ViewAnalytics, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showGraph() {
        var barChart: BarChart = findViewById(R.id.barchart) as BarChart
        barChart.visibility = View.VISIBLE
        var totalArray = IntArray(9)
        for(i in 0..8) totalArray[i] = 0
        var categoryValues : ArrayList<String> = ArrayList()
        categoryValues.add("Bills")
        categoryValues.add("Groceries")
        categoryValues.add("Entmnt")
        categoryValues.add("Fuel")
        categoryValues.add("Food")
        categoryValues.add("Health")
        categoryValues.add("Travel")
        categoryValues.add("Shopping")
        categoryValues.add("Other")

        var entries : ArrayList<BarEntry> = ArrayList()
        var labels : ArrayList<String> = ArrayList()

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

                            var pos =0
                            for(i in 0..8){
                                if(totalArray[i] != 0){
                                    entries.add(BarEntry(totalArray[i].toFloat(), pos))
                                    labels.add(categoryValues[i])
                                    pos++
                                }
                            }

                            var labelsArray = arrayOfNulls<String>(labels.size)
                            var index = 0
                            for (label in labels) {
                                labelsArray[index] = labels[index]
                                index++
                            }

                            var bardataset = BarDataSet(entries, "")
                            var data = BarData(labels, bardataset)
                            barChart.data = data
                            barChart.setDescription("")
                            bardataset.setColors(ColorTemplate.COLORFUL_COLORS)
                            data.setValueTextSize(8f)
                            var legend = barChart.legend
                            legend.isEnabled = false
                            barChart.invalidate()
                        }

                        override fun onFailure(e: HasuraException) {
                            Toast.makeText(this@ViewAnalytics, e.toString(), Toast.LENGTH_SHORT).show()
                        }
                    })

        } catch (e: JSONException) {
            Toast.makeText(this@ViewAnalytics, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if(position == 0){
            hidePieChart()
            showGraph()
        }
        else{
            hideGraph()
            showPieChart()
        }
    }

    private fun hideGraph(){
        val graph : BarChart = findViewById(R.id.barchart) as BarChart
        graph.visibility = View.INVISIBLE
    }

    private fun hidePieChart(){
        val piechart : PieChart = findViewById(R.id.piechart) as PieChart
        piechart.visibility = View.INVISIBLE
    }

}