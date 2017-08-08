package comexpensetracker.medium.extra_expensetracker

import android.view.View.OnLongClickListener
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.R.attr.description
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.R.attr.description
import android.R.attr.data







/**
 * Created by akash on 23/7/17.
 */
class ExpenseRecyclerViewAdapter : RecyclerView.Adapter<ExpenseViewHolder>() {

    var data:List<ExpenseRecord> = ArrayList<ExpenseRecord>()

    override fun onBindViewHolder(holder: ExpenseViewHolder?, position: Int) {
        val expenseRecord:ExpenseRecord = data[position]
        holder?.timestampText?.text = expenseRecord.getExpCreated()
        holder?.nameText?.text = expenseRecord.getExpName()
        holder?.amtText?.text = expenseRecord.getExpAmt().toString()
    }

    override fun getItemCount(): Int {
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
