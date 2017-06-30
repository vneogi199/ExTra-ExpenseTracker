package comexpensetracker.medium.extra_expensetracker

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import android.text.format.DateFormat
import android.util.Log
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


    var expenseTimestampText : EditText ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        expenseTimestampText = findViewById(R.id.expenseTimestampText) as EditText
        (expenseTimestampText as EditText).setOnClickListener{

            var calendar : Calendar = Calendar.getInstance()
            year = calendar.get(Calendar.YEAR)
            month = calendar.get(Calendar.MONTH)
            day = calendar.get(Calendar.DAY_OF_MONTH)

            var datePickerDialog : DatePickerDialog = DatePickerDialog(this@AddExpense, this@AddExpense, year, month, day)
            datePickerDialog.show()

        }

    }

    override fun onDateSet(view: DatePicker?, yearSelected: Int, monthSelected: Int, dayOfMonthSelected: Int) {
        yearFinal = yearSelected
        monthFinal = monthSelected + 1
        dayFinal = dayOfMonthSelected

        var calendar : Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR_OF_DAY)
        minute = calendar.get(Calendar.MINUTE)

        var timePickerDialog : TimePickerDialog = TimePickerDialog(this@AddExpense, this@AddExpense, hour, minute, DateFormat.is24HourFormat(this))
        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDaySelected: Int, minuteSelected: Int) {
        hourFinal = hourOfDaySelected
        minuteFinal=minuteSelected
        Log.d("TAG", yearFinal.toString() + "\n" + monthFinal.toString() +  "\n" + dayFinal.toString() +  "\n" + hourFinal.toString() +  "\n" + minuteFinal.toString())
    }

}