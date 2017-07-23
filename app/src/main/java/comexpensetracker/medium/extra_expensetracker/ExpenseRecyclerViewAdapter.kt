package comexpensetracker.medium.extra_expensetracker

import android.view.View.OnLongClickListener
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.R.attr.description
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup



/**
 * Created by akash on 23/7/17.
 */
class ExpenseRecyclerViewAdapter {

    var data: MutableList<ExpenseRecord> = ArrayList()

    interface Interactor {
        fun onTodoClicked(position: Int, record: ExpenseRecord)
        fun onTodoLongClicked(position: Int, record: ExpenseRecord)
    }

    var interactor: Interactor = null!!

    fun ToDoRecyclerViewAdapter(interactor: Interactor) {
        this.interactor = interactor
    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int) {
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_todo, parent, false)
        //return ExpenseViewHolder(view)
    }

    fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val todoRecord = data[position]
        //holder.description.setText(todoRecord.getTitle())
        //holder.checkbox.setChecked(todoRecord.getCompleted())

        /*
        if (todoRecord.getCompleted()) {
            holder.description.setPaintFlags(holder.description.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        } else {
            holder.description.setPaintFlags(holder.description.getPaintFlags() and Paint.STRIKE_THRU_TEXT_FLAG.inv())
        }

        holder.itemView.setOnLongClickListener(object : View.OnLongClickListener() {
            fun onLongClick(v: View): Boolean {
                interactor.onTodoLongClicked(position, todoRecord)
                return true
            }
        })

        holder.itemView.setOnClickListener(object : View.OnClickListener() {
            fun onClick(v: View) {
                interactor.onTodoClicked(position, todoRecord)
            }
        })

        holder.checkbox.setClickable(false)
    }

    fun getItemCount(): Int {
        return data.size
    }

    fun setData(recordList: MutableList<ExpenseRecord>) {
        this.data = recordList
        notifyDataSetChanged()
    }

    fun deleteData(position: Int, record: ExpenseRecord) {
        this.data.removeAt(position)
        notifyDataSetChanged()
    }

    fun updateData(position: Int, record: ExpenseRecord) {
        this.data[position] = record
        notifyDataSetChanged()
    }

    fun addData(record: ExpenseRecord) {
        this.data.add(record)
        notifyDataSetChanged()
    }
    */

}
}
