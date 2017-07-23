package comexpensetracker.medium.extra_expensetracker

import android.widget.CheckBox
import android.R.attr.description
import android.view.View
import android.widget.TextView



/**
 * Created by akash on 23/7/17.
 */
class ExpenseViewHolder {

    var checkbox: CheckBox = null!!
    var description: TextView = null!!

    fun ExpenseViewHolder(itemView: View) {
        //super(itemView)
        //description = itemView.findViewById(R.id.description) as TextView
        //checkbox = itemView.findViewById(R.id.checkbox) as CheckBox
    }

}
