package comexpensetracker.medium.extra_expensetracker

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.*
import io.hasura.sdk.Callback
import io.hasura.sdk.Hasura
import io.hasura.sdk.HasuraUser
import io.hasura.sdk.ProjectConfig
import io.hasura.sdk.exception.HasuraException
import io.hasura.sdk.exception.HasuraInitException
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class AddExpense : BaseActivity(), OnDateSetListener, OnTimeSetListener {

    var day : Int = 0
    var month : Int = 0
    var year : Int = 0
    var hour : Int = 0
    var minute : Int = 0

    var dayFinal : Int = 0
    var monthFinal : Int = 0
    var yearFinal : Int = 0
    var hourFinal : Int = 0
    var minuteFinal : Int = 0

    var bills_status = 0
    var groceries_status = 0
    var entertainment_status = 0
    var fuel_status = 0
    var food_status = 0
    var health_status = 0
    var travel_status = 0
    var shopping_status = 0
    var other_status = 0

    var expenseTimestampText : EditText ?= null
    val client = Hasura.getClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        try {
            Hasura.setProjectConfig(ProjectConfig.Builder()
                    .setCustomBaseDomain("extraexpensetracker.hasura.me").enableOverHttp()
                    .build())
                    .initialise(this)
        } catch (e: HasuraInitException) {
            e.printStackTrace()
        }

        var user : HasuraUser = client.user
        if (user.isLoggedIn()) {

        } else {
            Toast.makeText(this@AddExpense, "Please login", Toast.LENGTH_LONG).show()
            var intent : Intent = Intent(this@AddExpense, LoginForm::class.java)
            startActivity(intent)
        }


        expenseTimestampText = findViewById(R.id.expenseTimestampText) as EditText
        (expenseTimestampText as EditText).setOnClickListener{

            val calendar : Calendar = Calendar.getInstance()
            year = calendar.get(Calendar.YEAR)
            month = calendar.get(Calendar.MONTH)
            day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog : DatePickerDialog = DatePickerDialog(this@AddExpense, this@AddExpense, year, month, day)
            datePickerDialog.show()

        }

        val billsLayout = findViewById(R.id.BillsLayout) as LinearLayout
        val billsTick = findViewById(R.id.TickIconBills) as ImageView

        billsLayout.setOnClickListener({
            toggleTickmark(billsTick)
        })

        val groceriesLayout = findViewById(R.id.GroceriesLayout) as LinearLayout
        val groceriesTick = findViewById(R.id.TickIconGroceries) as ImageView

        groceriesLayout.setOnClickListener({
            toggleTickmark(groceriesTick)
        })

        val entertainmentLayout = findViewById(R.id.EntertainmentLayout) as LinearLayout
        val entertainmentTick = findViewById(R.id.TickIconEntertainment) as ImageView

        entertainmentLayout.setOnClickListener({
            toggleTickmark(entertainmentTick)
        })

        val fuelLayout = findViewById(R.id.FuelLayout) as LinearLayout
        val fuelTick = findViewById(R.id.TickIconFuel) as ImageView

        fuelLayout.setOnClickListener({
            toggleTickmark(fuelTick)
        })

        val foodLayout = findViewById(R.id.FoodLayout) as LinearLayout
        val foodTick = findViewById(R.id.TickIconFood) as ImageView

        foodLayout.setOnClickListener({
            toggleTickmark(foodTick)
        })

        val healthLayout = findViewById(R.id.HealthLayout) as LinearLayout
        val healthTick = findViewById(R.id.TickIconHealth) as ImageView

        healthLayout.setOnClickListener({
            toggleTickmark(healthTick)
        })

        val travelLayout = findViewById(R.id.TravelLayout) as LinearLayout
        val travelTick = findViewById(R.id.TickIconTravel) as ImageView

        travelLayout.setOnClickListener({
            toggleTickmark(travelTick)
        })

        val shoppingLayout = findViewById(R.id.ShoppingLayout) as LinearLayout
        val shoppingTick = findViewById(R.id.TickIconShopping) as ImageView

        shoppingLayout.setOnClickListener({
            toggleTickmark(shoppingTick)
        })

        val otherLayout = findViewById(R.id.OtherLayout) as LinearLayout
        val otherTick = findViewById(R.id.TickIconOther) as ImageView

        otherLayout.setOnClickListener({
            toggleTickmark(otherTick)
        })

    }

    override fun onDateSet(view: DatePicker?, yearSelected: Int, monthSelected: Int, dayOfMonthSelected: Int) {
        yearFinal = yearSelected
        monthFinal = monthSelected + 1
        dayFinal = dayOfMonthSelected

        val calendar : Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR_OF_DAY)
        minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog : TimePickerDialog = TimePickerDialog(this@AddExpense, this@AddExpense, hour, minute, DateFormat.is24HourFormat(this))
        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDaySelected: Int, minuteSelected: Int) {
        hourFinal = hourOfDaySelected
        minuteFinal = minuteSelected
        Log.d("TAG", yearFinal.toString() + "\n" + monthFinal.toString() +  "\n" + dayFinal.toString() +  "\n" + hourFinal.toString() +  "\n" + minuteFinal.toString())
        expenseTimestampText?.text = Editable.Factory.getInstance().newEditable(yearFinal.toString() + " " + monthFinal.toString() +  " " + dayFinal.toString() +  " " + hourFinal.toString() +  " " + minuteFinal.toString())
    }

    fun toggleTickmark(tick : View){
            if(tick.visibility==View.GONE)
                tick.visibility=View.VISIBLE
            else if(tick.visibility==View.VISIBLE)
                tick.visibility=View.GONE
    }

    fun insertExpense(v : View){
        var expenseNameText : EditText = findViewById(R.id.expenseNameText) as EditText
        try {
            var jsonQuery = "{\"type\":\"insert\",\"args\":{\"table\":\"expense\",\"objects\":[ {\"user_id\":\"1\", \"exp_name\":\"News\", \"exp_amt\":\"100\", \"exp_created\":\"2017-06-24T18:50:24.029984+00:00\",\"exp_category\":\"2\"}]}}"
            val jsonObject = JSONObject(jsonQuery)

            client.useDataService()
                    .setRequestBody(jsonObject)
                    .expectResponseType(InsertExpenseResult::class.java)
                    .enqueue(object : Callback<InsertExpenseResult, HasuraException> {
                        override fun onSuccess(p0: InsertExpenseResult?) {
                            Toast.makeText(applicationContext, "Expense Recorded", Toast.LENGTH_LONG).show()
                            hideProgressIndicator()
                        }
                        override fun onFailure(e: HasuraException) {
                            hideProgressIndicator()
                            Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
                        }
                    })

        } catch (e: JSONException) {
            Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
        }
    }
}