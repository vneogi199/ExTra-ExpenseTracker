package comexpensetracker.medium.extra_expensetracker


import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amulyakhare.textdrawable.TextDrawable
import java.util.*


/**
 * Created by akash on 23/7/17.
 */
class ExpenseRecyclerViewAdapter : RecyclerView.Adapter<ExpenseViewHolder>() {

    var data:List<ExpenseRecord> = ArrayList()

    override fun onBindViewHolder(holder: ExpenseViewHolder?, position: Int) {
        val expenseRecord:ExpenseRecord = data[position]
        var colorString = ""
        when(expenseRecord.getExpCategory()){
            "Bills" -> colorString = "#b1d7e9"
            "Groceries" -> colorString = "#FFB74D"
            "Entertainment" -> colorString = "#ef5350"
            "Fuel" -> colorString = "#BCAAA4"
            "Food" -> colorString = "#7986CB"
            "Health" -> colorString = "#81C784"
            "Travel" -> colorString = "#F8BBD0"
            "Shopping" -> colorString = "#BCAAA4"
            "Other" -> colorString = "#D1C4E9"
        }
        holder?.timestampText?.text = expenseRecord.getExpCreated()
        holder?.nameText?.text = expenseRecord.getExpName()
        holder?.amtText?.text = "₹" + expenseRecord.getExpAmt().toString()
        val categoryLetter = expenseRecord.getExpCategory()?.substring(0,1)
        val drawable2 = TextDrawable.builder().buildRound(categoryLetter, Color.parseColor(colorString))
        holder?.categoryIcon?.setImageDrawable(drawable2)
    }

    override fun getItemCount(): Int {
        Log.d("TAG", data.size.toString())
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ExpenseViewHolder {
        val view:View = LayoutInflater.from(parent?.context).inflate(R.layout.expense_list_item, parent, false)
        return ExpenseViewHolder(view)
    }

    fun setExpense(recordList: List<ExpenseRecord>) {
        this.data = recordList
        notifyDataSetChanged()
    }
}
