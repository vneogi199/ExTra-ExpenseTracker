package comexpensetracker.medium.extra_expensetracker

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.format.DateFormat
import android.view.View
import android.widget.*
import io.hasura.sdk.Callback
import io.hasura.sdk.Hasura
import io.hasura.sdk.HasuraUser
import io.hasura.sdk.ProjectConfig
import io.hasura.sdk.exception.HasuraException
import io.hasura.sdk.exception.HasuraInitException
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class AddExpense : BaseActivity(), OnDateSetListener, OnTimeSetListener {

    private var day : Int = 0
    private var month : Int = 0
    private var year : Int = 0
    private var hour : Int = 0
    private var minute : Int = 0

    private var dayFinal : Int = 0
    private var monthFinal : Int = 0
    private var yearFinal : Int = 0
    private var hourFinal : Int = 0
    private var minuteFinal : Int = 0

    private var date : Date? = null
    var s: String = ""
    var expenseTimestampText : EditText? = null

    private val client = Hasura.getClient()!!
    var user : HasuraUser = Hasura.getClient().user

    var expenseNameText : EditText ?= null
    var expenseAmtText : EditText ?= null
    var selectedCategory = 0
    var noteInput : String = ""
    var tagInput : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)
        expenseNameText = findViewById(R.id.expenseNameText) as EditText
        expenseAmtText = findViewById(R.id.expenseAmtText) as EditText
        try {
            Hasura.setProjectConfig(ProjectConfig.Builder()
                    .setCustomBaseDomain("cobalt21.hasura-app.io")
                    //.enableOverHttp()
                    .build())
                    .initialise(this)
        } catch (e: HasuraInitException) {
            e.printStackTrace()
        }

        if (user.isLoggedIn) {

        } else {
            Toast.makeText(this@AddExpense, "Please login", Toast.LENGTH_LONG).show()
            val intent = Intent(this@AddExpense, LoginForm::class.java)
            startActivity(intent)
        }


        expenseTimestampText = findViewById(R.id.expenseTimestampText) as EditText
        (expenseTimestampText as EditText).setOnClickListener{

            val calendar : Calendar = Calendar.getInstance()
            year = calendar.get(Calendar.YEAR)
            month = calendar.get(Calendar.MONTH)
            day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this@AddExpense, this@AddExpense, year, month, day)
            datePickerDialog.show()

        }

        val billsLayout = findViewById(R.id.BillsLayout) as LinearLayout
        val billsTick = findViewById(R.id.TickIconBills) as ImageView

        val groceriesLayout = findViewById(R.id.GroceriesLayout) as LinearLayout
        val groceriesTick = findViewById(R.id.TickIconGroceries) as ImageView

        val entertainmentLayout = findViewById(R.id.EntertainmentLayout) as LinearLayout
        val entertainmentTick = findViewById(R.id.TickIconEntertainment) as ImageView

        val fuelLayout = findViewById(R.id.FuelLayout) as LinearLayout
        val fuelTick = findViewById(R.id.TickIconFuel) as ImageView

        val foodLayout = findViewById(R.id.FoodLayout) as LinearLayout
        val foodTick = findViewById(R.id.TickIconFood) as ImageView

        val healthLayout = findViewById(R.id.HealthLayout) as LinearLayout
        val healthTick = findViewById(R.id.TickIconHealth) as ImageView

        val travelLayout = findViewById(R.id.TravelLayout) as LinearLayout
        val travelTick = findViewById(R.id.TickIconTravel) as ImageView

        val shoppingLayout = findViewById(R.id.ShoppingLayout) as LinearLayout
        val shoppingTick = findViewById(R.id.TickIconShopping) as ImageView

        val otherLayout = findViewById(R.id.OtherLayout) as LinearLayout
        val otherTick = findViewById(R.id.TickIconOther) as ImageView

        billsLayout.setOnClickListener({
            if(billsTick.visibility==View.GONE){
                billsTick.visibility=View.VISIBLE
                groceriesTick.visibility=View.GONE
                entertainmentTick.visibility=View.GONE
                fuelTick.visibility=View.GONE
                foodTick.visibility=View.GONE
                healthTick.visibility=View.GONE
                travelTick.visibility=View.GONE
                shoppingTick.visibility=View.GONE
                otherTick.visibility=View.GONE
                selectedCategory = 1
            }
            else if(billsTick.visibility==View.VISIBLE){
                billsTick.visibility=View.GONE
                selectedCategory = 0
            }
        })

        groceriesLayout.setOnClickListener({
            if(groceriesTick.visibility==View.GONE){
                billsTick.visibility=View.GONE
                groceriesTick.visibility=View.VISIBLE
                entertainmentTick.visibility=View.GONE
                fuelTick.visibility=View.GONE
                foodTick.visibility=View.GONE
                healthTick.visibility=View.GONE
                travelTick.visibility=View.GONE
                shoppingTick.visibility=View.GONE
                otherTick.visibility=View.GONE
                selectedCategory = 2
            }
            else if(groceriesTick.visibility==View.VISIBLE){
                groceriesTick.visibility=View.GONE
                selectedCategory = 0
            }
        })

        entertainmentLayout.setOnClickListener({
            if(entertainmentTick.visibility==View.GONE){
                billsTick.visibility=View.GONE
                groceriesTick.visibility=View.GONE
                entertainmentTick.visibility=View.VISIBLE
                fuelTick.visibility=View.GONE
                foodTick.visibility=View.GONE
                healthTick.visibility=View.GONE
                travelTick.visibility=View.GONE
                shoppingTick.visibility=View.GONE
                otherTick.visibility=View.GONE
                selectedCategory = 3
            }
            else if(entertainmentTick.visibility==View.VISIBLE){
                entertainmentTick.visibility=View.GONE
                selectedCategory = 0
            }
        })

        fuelLayout.setOnClickListener({
            if(fuelTick.visibility==View.GONE){
                billsTick.visibility=View.GONE
                groceriesTick.visibility=View.GONE
                entertainmentTick.visibility=View.GONE
                fuelTick.visibility=View.VISIBLE
                foodTick.visibility=View.GONE
                healthTick.visibility=View.GONE
                travelTick.visibility=View.GONE
                shoppingTick.visibility=View.GONE
                otherTick.visibility=View.GONE
                selectedCategory  = 4
            }
            else if(fuelTick.visibility==View.VISIBLE){
                fuelTick.visibility=View.GONE
                selectedCategory = 0
            }
        })

        foodLayout.setOnClickListener({
            if(foodTick.visibility==View.GONE){
                billsTick.visibility=View.GONE
                groceriesTick.visibility=View.GONE
                entertainmentTick.visibility=View.GONE
                fuelTick.visibility=View.GONE
                foodTick.visibility=View.VISIBLE
                healthTick.visibility=View.GONE
                travelTick.visibility=View.GONE
                shoppingTick.visibility=View.GONE
                otherTick.visibility=View.GONE
                selectedCategory = 5
            }
            else if(foodTick.visibility==View.VISIBLE) {
                foodTick.visibility = View.GONE
                selectedCategory = 0
            }
        })

        healthLayout.setOnClickListener({
            if(healthTick.visibility==View.GONE){
                billsTick.visibility=View.GONE
                groceriesTick.visibility=View.GONE
                entertainmentTick.visibility=View.GONE
                fuelTick.visibility=View.GONE
                foodTick.visibility=View.GONE
                healthTick.visibility=View.VISIBLE
                travelTick.visibility=View.GONE
                shoppingTick.visibility=View.GONE
                otherTick.visibility=View.GONE
                selectedCategory = 6
            }
            else if(healthTick.visibility==View.VISIBLE) {
                healthTick.visibility = View.GONE
                selectedCategory = 0
            }
        })

        travelLayout.setOnClickListener({
            if(travelTick.visibility==View.GONE){
                billsTick.visibility=View.GONE
                groceriesTick.visibility=View.GONE
                entertainmentTick.visibility=View.GONE
                fuelTick.visibility=View.GONE
                foodTick.visibility=View.GONE
                healthTick.visibility=View.GONE
                travelTick.visibility=View.VISIBLE
                shoppingTick.visibility=View.GONE
                otherTick.visibility=View.GONE
                selectedCategory = 7
            }
            else if(travelTick.visibility==View.VISIBLE) {
                travelTick.visibility = View.GONE
                selectedCategory = 0
            }
        })

        shoppingLayout.setOnClickListener({
            if(shoppingTick.visibility==View.GONE){
                billsTick.visibility=View.GONE
                groceriesTick.visibility=View.GONE
                entertainmentTick.visibility=View.GONE
                fuelTick.visibility=View.GONE
                foodTick.visibility=View.GONE
                healthTick.visibility=View.GONE
                travelTick.visibility=View.GONE
                shoppingTick.visibility=View.VISIBLE
                otherTick.visibility=View.GONE
                selectedCategory = 8
            }
            else if(shoppingTick.visibility==View.VISIBLE) {
                shoppingTick.visibility = View.GONE
                selectedCategory = 0
            }
        })

        otherLayout.setOnClickListener({
            if(otherTick.visibility==View.GONE){
                billsTick.visibility=View.GONE
                groceriesTick.visibility=View.GONE
                entertainmentTick.visibility=View.GONE
                fuelTick.visibility=View.GONE
                foodTick.visibility=View.GONE
                healthTick.visibility=View.GONE
                travelTick.visibility=View.GONE
                shoppingTick.visibility=View.GONE
                otherTick.visibility=View.VISIBLE
                selectedCategory = 9
            }
            else if(otherTick.visibility==View.VISIBLE) {
                otherTick.visibility = View.GONE
                selectedCategory = 0
            }
        })
    }

    override fun onDateSet(view: DatePicker?, yearSelected: Int, monthSelected: Int, dayOfMonthSelected: Int) {
        yearFinal = yearSelected
        monthFinal = monthSelected + 1
        dayFinal = dayOfMonthSelected

        val calendar : Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR_OF_DAY)
        minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this@AddExpense, this@AddExpense, hour, minute, DateFormat.is24HourFormat(this))
        timePickerDialog.show()
    }

    private fun prependZero(value : Int): String {
        if(value.toString().isEmpty()) return "00"
        else if (value.toString().length == 1) return "0" +value.toString()
        else return value.toString()
    }
    override fun onTimeSet(view: TimePicker?, hourOfDaySelected: Int, minuteSelected: Int) {
        hourFinal = hourOfDaySelected
        minuteFinal = minuteSelected

        s = prependZero(dayFinal) + "/" + prependZero(monthFinal) + "/" + yearFinal.toString()+ " " +prependZero(hourFinal)+ ":" +prependZero(minuteFinal)
        expenseTimestampText?.text = Editable.Factory.getInstance().newEditable(s)
    }

    fun insertExpense(v : View){
        if(expenseNameText?.text.toString().isEmpty()){
            Toast.makeText(applicationContext, "Please enter the expense name", Toast.LENGTH_SHORT).show()
            return@insertExpense
        }
        if(expenseAmtText?.text.toString().isEmpty()){
            Toast.makeText(applicationContext, "Please enter the expense amount", Toast.LENGTH_SHORT).show()
            return@insertExpense
        }
        if(expenseTimestampText?.text.toString().isEmpty()){
            Toast.makeText(applicationContext, "Please select the expense date and time", Toast.LENGTH_SHORT).show()
            return@insertExpense
        }
        if(selectedCategory==0){
            Toast.makeText(applicationContext, "Please select the expense category", Toast.LENGTH_SHORT).show()
            return@insertExpense
        }
        val expenseNotesText: EditText = findViewById(R.id.expenseNotesText) as EditText
        if(expenseNotesText.text.toString().isEmpty()) {
            noteInput = ""
        }
        else{
            noteInput = expenseNotesText.text.toString()
        }
        val expenseTagText : EditText = findViewById(R.id.expenseTagsText) as EditText
        if(expenseTagText.text.toString().isEmpty()) {
            tagInput = ""
        }
        else{
            tagInput = expenseTagText.text.toString()
        }
        try {
            //var jsonQuery = "{\"type\":\"insert\",\"args\":{\"table\":\"expense\",\"objects\":[ {\"user_id\":\"1\", \"exp_name\":\"News\", \"exp_amt\":\"100\", \"exp_created\":\"2017-06-24T18:50:24.029984+00:00\",\"exp_category\":\"2\"}]}}"
            //val jsonObject = JSONObject(jsonQuery)
            val nameJSON = JSONObject()
            nameJSON.put("user_id", user.id)
            nameJSON.put("exp_name", expenseNameText?.text.toString())
            nameJSON.put("exp_amt", expenseAmtText?.text.toString())
            nameJSON.put("exp_created", expenseTimestampText?.text.toString())
            nameJSON.put("exp_category", selectedCategory)
            nameJSON.put("exp_note", noteInput)
            nameJSON.put("exp_tag", tagInput)

            val colsList = JSONArray()
            colsList.put(nameJSON)

            val args = JSONObject()
            args.put("table", "expense")
            args.put("objects", colsList)

            val addExpenseJSON = JSONObject()
            addExpenseJSON.put("type", "insert")
            addExpenseJSON.put("args", args)



            client.useDataService()
                    .setRequestBody(addExpenseJSON)
                    .expectResponseType(InsertExpenseResult::class.java)
                    .enqueue(object : Callback<InsertExpenseResult, HasuraException> {
                        override fun onSuccess(p0: InsertExpenseResult?) {
                            Toast.makeText(applicationContext, "Expense Recorded", Toast.LENGTH_LONG).show()
                            hideProgressIndicator()
                        }
                        override fun onFailure(e: HasuraException) {
                            hideProgressIndicator()
                            Toast.makeText(applicationContext, e.toString() + user.id, Toast.LENGTH_LONG).show()
                        }
                    })

        } catch (e: JSONException) {
            Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
        }
    }
}