package comexpensetracker.medium.extra_expensetracker

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.text.format.DateFormat
import android.util.Log
import android.widget.*
import java.util.*

class AddExpense : AppCompatActivity(), OnDateSetListener, OnTimeSetListener {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

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

        billsLayout.setOnClickListener(View.OnClickListener {
            toggleTickmark(billsTick)
        })

        val groceriesLayout = findViewById(R.id.GroceriesLayout) as LinearLayout
        val groceriesTick = findViewById(R.id.TickIconGroceries) as ImageView

        groceriesLayout.setOnClickListener(View.OnClickListener {
            toggleTickmark(groceriesTick)
        })

        val entertainmentLayout = findViewById(R.id.EntertainmentLayout) as LinearLayout
        val entertainmentTick = findViewById(R.id.TickIconEntertainment) as ImageView

        entertainmentLayout.setOnClickListener(View.OnClickListener {
            toggleTickmark(entertainmentTick)
        })

        val fuelLayout = findViewById(R.id.FuelLayout) as LinearLayout
        val fuelTick = findViewById(R.id.TickIconFuel) as ImageView

        fuelLayout.setOnClickListener(View.OnClickListener {
            toggleTickmark(fuelTick)
        })

        val foodLayout = findViewById(R.id.FoodLayout) as LinearLayout
        val foodTick = findViewById(R.id.TickIconFood) as ImageView

        foodLayout.setOnClickListener(View.OnClickListener {
            toggleTickmark(foodTick)
        })

        val healthLayout = findViewById(R.id.HealthLayout) as LinearLayout
        val healthTick = findViewById(R.id.TickIconHealth) as ImageView

        healthLayout.setOnClickListener(View.OnClickListener {
            toggleTickmark(healthTick)
        })

        val travelLayout = findViewById(R.id.TravelLayout) as LinearLayout
        val travelTick = findViewById(R.id.TickIconTravel) as ImageView

        travelLayout.setOnClickListener(View.OnClickListener {
            toggleTickmark(travelTick)
        })

        val shoppingLayout = findViewById(R.id.ShoppingLayout) as LinearLayout
        val shoppingTick = findViewById(R.id.TickIconShopping) as ImageView

        shoppingLayout.setOnClickListener(View.OnClickListener {
            toggleTickmark(shoppingTick)
        })

        val otherLayout = findViewById(R.id.OtherLayout) as LinearLayout
        val otherTick = findViewById(R.id.TickIconOther) as ImageView

        otherLayout.setOnClickListener(View.OnClickListener {
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
        AddExpenseQuery(expenseNameText.toString(), 1)
        Toast.makeText(this, "insertExpense called", Toast.LENGTH_LONG).show()
    }
}
